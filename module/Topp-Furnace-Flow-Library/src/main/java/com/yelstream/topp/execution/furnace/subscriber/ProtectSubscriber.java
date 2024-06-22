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
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Passively protected subscriber.
 * <p>
 *     Intended for verification, diagnosis and repair of faulty implementations.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-29
 *
 * @param <T> Type of item.
 * @param <S> Type of subscriber.
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class ProtectSubscriber<T,S extends Flow.Subscriber<T>> implements Flow.Subscriber<T> {

    private final AtomicBoolean active=new AtomicBoolean(true);

    private final S subscriber;

    private ProtectSubscription<Flow.Subscription> subscription;

    @SuppressWarnings("java:S1066")
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        if (active.get()) {
            if (this.subscription==null) {
                this.subscription=ProtectSubscription.of(subscription);
                subscriber.onSubscribe(subscription);
            }
        }
    }

    @Override
    public void onNext(T item) {
        if (active.get()) {
            subscriber.onNext(item);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!active.compareAndSet(true,false)) {
            subscriber.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (!active.compareAndSet(true,false)) {
            subscriber.onComplete();
        }
    }
}
