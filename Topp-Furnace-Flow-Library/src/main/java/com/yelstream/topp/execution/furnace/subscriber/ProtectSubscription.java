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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Passively protected subscription.
 * <p>
 *     Intended for verification, diagnosis and repair of faulty implementations.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-29
 *
 * @param <P> Type of subscription.
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class ProtectSubscription<P extends Flow.Subscription> implements Flow.Subscription {
    private final AtomicBoolean active=new AtomicBoolean(true);

    @Getter
    private final P subscription;

    @Override
    public void request(long n) {
        if (active.get()) {
            subscription.request(n);
        }
    }

    @Override
    public void cancel() {
        if (active.compareAndSet(true,false)) {
            subscription.cancel();
        }
    }
}
