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

package com.yelstream.topp.execution.furnace.subscriber;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor(staticName="of")
public class SimpleSubscriber<T,R> implements Flow.Subscriber<T> {

    private Flow.Subscriber<? super R> subscriber;  //TODO: Make this a subscriber registry!

    private final int initialRequestCount=1;

    private Flow.Subscription subscription;

    private final Function<T,R> computation;

    private final BiConsumer<R,Flow.Subscriber<? super R>> resultSubmit;

    private final BiConsumer<R,Flow.Subscription> request;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription=subscription;
        subscription.request(initialRequestCount);
    }

    @Override
    public void onNext(T item) {
        // Perform computation on the item, for example, multiply it by 2
        //int result = item * 2;
        R result=computation.apply(item);

        // Pass the computed result downstream
        //subscriber.onNext(result);
        resultSubmit.accept(result,subscriber);

        // Request the next element
        //subscription.request(1);
        request.accept(result,subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
