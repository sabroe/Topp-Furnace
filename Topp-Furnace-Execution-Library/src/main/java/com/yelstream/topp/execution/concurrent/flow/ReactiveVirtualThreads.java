/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.execution.concurrent.flow;

import java.time.Duration;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ReactiveVirtualThreads {

    // https://medium.com/hprog99/virtual-threads-in-java-unlocking-high-throughput-concurrency-55606100b6be

    public static void main(String[] args) {
//        Supplier<ExecutorService> executorSupplier = ()->Executors.newVirtualThreadPerTaskExecutor();
        Supplier<ExecutorService> executorSupplier = ()->Executors.newFixedThreadPool(100);
        try (ExecutorService executor = executorSupplier.get()) {
            SubmissionPublisher<String> publisher = new SubmissionPublisher<>(executor, Flow.defaultBufferSize());
            publisher.subscribe(new Subscriber<>() {
                private Subscription subscription;

                @Override
                public void onSubscribe(Subscription subscription) {
                    this.subscription = subscription;
                    subscription.request(3);
                }
int count=0;
                @Override
                public void onNext(String item) {
                    System.out.println("Received: " + item);
                    try {
                        Thread.sleep(Duration.ofSeconds(5));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
//                    subscription.request(1);
                    count++;
                    if (count==3) {
                        subscription.cancel();
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onComplete() {
                    System.out.println("Completed");
                }
            });

            for (int i=0; i<10; i++) {
                publisher.submit("Hello, Reactive World with Virtual Threads! "+i);
            }
            System.out.println("All submitted!");
            publisher.close();
        }
    }
}
