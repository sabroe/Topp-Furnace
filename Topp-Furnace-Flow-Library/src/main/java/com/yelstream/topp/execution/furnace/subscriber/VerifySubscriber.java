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

import com.yelstream.topp.standard.lang.Runnables;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Actively verifying subscriber.
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
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of",access= AccessLevel.PRIVATE)
public class VerifySubscriber<T,S extends Flow.Subscriber<T>> implements Flow.Subscriber<T> {

    private final AtomicBoolean active=new AtomicBoolean(true);

    private final S subscriber;

    private VerifySubscription<Flow.Subscription> subscription;

    private final Runnable subscribeActiveViolationAction;

    private final Runnable resubscribeViolationAction;

    private final Runnable nextActiveViolationAction;

    private final Runnable errorActiveViolationAction;

    private final Runnable completeActiveViolationAction;

    private final Runnable invalidInvocationCountAction;

    public static final Runnable SUBSCRIBE_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to subscribe; subscriber has been completed!"); };

    public static final Runnable RESUBSCRIBE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to resubscribe; subscription is already set!"); };

    public static final Runnable NEXT_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to process next item; subscriber has been completed!"); };

    public static final Runnable ERROR_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to register error; subscriber has been completed!"); };

    public static final Runnable COMPLETE_ACTIVE_VIOLATION_ACTION=()->{ throw new IllegalStateException("Failure to complete; subscriber has been completed!"); };

    public static final Runnable INVALID_INVOCATION_COUNT_ACTION=()->{ throw new IllegalStateException("Failure to process next item; item not requested!"); };

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        if (!active.get()) {
            Runnables.run(subscribeActiveViolationAction);
        } else {
            if (this.subscription!=null) {
                Runnables.run(resubscribeViolationAction);
            } else {
                this.subscription=VerifySubscription.of(subscription);
                subscriber.onSubscribe(subscription);
            }
        }
    }

    @Override
    public void onNext(T item) {
        if (!active.get()) {
            Runnables.run(nextActiveViolationAction);
        } else {
            if (!subscription.registerInvocation()) {
                Runnables.run(invalidInvocationCountAction);
            }
            subscriber.onNext(item);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!active.compareAndSet(true,false)) {
            Runnables.run(errorActiveViolationAction);
        } else {
            subscriber.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (!active.compareAndSet(true,false)) {
            Runnables.run(completeActiveViolationAction);
        } else {
            subscriber.onComplete();
        }
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder<T,S extends Flow.Subscriber<T>> {
        private Runnable subscribeActiveViolationAction=SUBSCRIBE_ACTIVE_VIOLATION_ACTION;

        private Runnable resubscribeViolationAction=RESUBSCRIBE_VIOLATION_ACTION;

        private Runnable nextActiveViolationAction=NEXT_ACTIVE_VIOLATION_ACTION;

        private Runnable errorActiveViolationAction=ERROR_ACTIVE_VIOLATION_ACTION;

        private Runnable completeActiveViolationAction=COMPLETE_ACTIVE_VIOLATION_ACTION;

        private Runnable invalidInvocationCountAction=INVALID_INVOCATION_COUNT_ACTION;
    }

    public static <T,S extends Flow.Subscriber<T>> VerifySubscriber<T,S> of(S subscriber) {
        VerifySubscriber.Builder<T,S> builder=builder();
        builder.subscriber(subscriber);
        return builder.build();
    }
}
