/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.execution.furnace;

import com.yelstream.topp.execution.furnace.submitter.Submitter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicLong;

public class SyncPublisher<T> implements Flow.Publisher<T>, Submitter<T> {

    @RequiredArgsConstructor
    private class SubscriberInfo<T> implements Flow.Subscription {
        @Getter
        private final Flow.Subscriber<? super T> subscriber;

        public AtomicLong requested=new AtomicLong();

        @Override
        public void request(long n) {
            if (n>0) {
                requested.addAndGet(n);
                trigger();
            }
        }

        @Override
        public void cancel() {
            subscribers.remove(subscriber);
        }
    }

    private Map<Flow.Subscriber<? super T>,SubscriberInfo<T>> subscribers=new ConcurrentHashMap<>();

    private ExecutorService subscriptionExecutor=Executors.newFixedThreadPool(5);

    private ExecutorService itemExecutor=Executors.newFixedThreadPool(5);

    private Queue<T> itemQueue=new ArrayBlockingQueue<>(1000);

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        SubscriberInfo<T> info=new SubscriberInfo<>(subscriber);
        subscribers.put(subscriber,info);

        subscriptionExecutor.execute(()->subscriber.onSubscribe(info));
    }

    @Override
    public void submit(T item) {
        itemQueue.add(item);
        trigger();
    }

    private void trigger() {
        T item=itemQueue.poll();
        if (item!=null) {
            subscribers.forEach((subscriber,info)-> {
                itemExecutor.execute(()->{
                    try {
                        subscriber.onNext(item);
                    } finally {
                        trigger();
                    }
                });
            });
        }
    }
}
