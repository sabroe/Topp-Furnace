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

package com.yelstream.topp.execution.furnace.subscriber;

import com.yelstream.topp.standard.lang.Runnables;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Actively verifying subscription.
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
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
public class VerifySubscription<P extends Flow.Subscription> implements Flow.Subscription {

    private final AtomicBoolean active=new AtomicBoolean(true);

    @Getter
    private final P subscription;

    private final AtomicLong accumulatedRequestCount=new AtomicLong();

    private final AtomicLong remainingInvocationCount=new AtomicLong();

    private final Runnable requestActiveViolationAction;

    private final Runnable cancelActiveViolationAction;

    public static final Runnable REQUEST_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to request; subscription has been cancelled!"); };

    public static final Runnable CANCEL_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to cancel; subscription has been cancelled!"); };

    public boolean isActive() {
        return active.get();
    }

    public long getAccumulatedRequestCount() {
        return accumulatedRequestCount.get();
    }

    public long getRemainingInvocationCount() {
        return remainingInvocationCount.get();
    }

    public boolean registerInvocation() {
        return remainingInvocationCount.decrementAndGet()<0L;
    }

    @Override
    public void request(long n) {
        if (!active.get()) {
            Runnables.run(requestActiveViolationAction);
        } else {
            accumulatedRequestCount.addAndGet(n);
            remainingInvocationCount.addAndGet(n);
            subscription.request(n);
        }
    }

    @Override
    public void cancel() {
        if (!active.compareAndSet(true,false)) {
            Runnables.run(cancelActiveViolationAction);
        } else {
            subscription.cancel();
        }
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder<P extends Flow.Subscription> {
        private Runnable requestActiveViolationAction=REQUEST_ACTIVE_VIOLATION_ACTION;

        private Runnable cancelActiveViolationAction=CANCEL_ACTIVE_VIOLATION_ACTION;
    }

    public static <P extends Flow.Subscription> VerifySubscription<P> of(P subscription) {
        Builder<P> builder=builder();
        builder.subscription(subscription);
        return builder.build();
    }
}
