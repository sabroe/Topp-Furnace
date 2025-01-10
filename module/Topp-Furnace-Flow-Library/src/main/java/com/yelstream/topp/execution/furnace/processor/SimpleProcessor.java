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

package com.yelstream.topp.execution.furnace.processor;

import java.util.concurrent.Flow;

public class SimpleProcessor<T,R> implements Flow.Subscriber<T>, Flow.Publisher<R> {

    private Flow.Subscriber<? super R> subscriber;

    private Flow.Subscription subscription;

    @Override
    public void subscribe(Flow.Subscriber<? super R> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // Request the first element when subscribed
    }

    @Override
    public void onNext(T item) {
        // Perform computation on the item, for example, multiply it by 2
//        int result = item * 2;

        // Pass the computed result downstream
//        subscriber.onNext(result);

        // Request the next element
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        // Handle errors if any
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        // Complete the subscriber when the computation is done
        subscriber.onComplete();
    }
}
