package com.yelstream.topp.execution.furnace.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

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
@RequiredArgsConstructor(staticName="of")
public class VerifySubscriber<T,S extends Flow.Subscriber<T>> implements Flow.Subscriber<T> {

    private final AtomicBoolean active=new AtomicBoolean(true);

    private final S subscriber;

    private VerifySubscription<Flow.Subscription> subscription;

    @lombok.Builder.Default
    private final Runnable subscribeActiveViolationAction=()->{ throw new IllegalStateException("Failure to subscribe; subscriber has been completed!"); };

    @lombok.Builder.Default
    private final Runnable resubscribeViolationAction=()->{ throw new IllegalStateException("Failure to resubscribe; subscription is already set!"); };

    @lombok.Builder.Default
    private final Runnable nextActiveViolationAction=()->{ throw new IllegalStateException("Failure to process next item; subscriber has been completed!"); };

    @lombok.Builder.Default
    private final Runnable errorActiveViolationAction=()->{ throw new IllegalStateException("Failure to register error; subscriber has been completed!"); };

    @lombok.Builder.Default
    private final Runnable completeActiveViolationAction=()->{ throw new IllegalStateException("Failure to complete; subscriber has been completed!"); };

    @lombok.Builder.Default
    @SuppressWarnings("java:S1117")
    private final BiConsumer<VerifySubscription<Flow.Subscription>,T> invalidInvocationCountAction=
        (subscription,item)->{ throw new IllegalStateException("Failure to process next item; item not requested!"); };

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        if (!active.get()) {
            subscribeActiveViolationAction.run();
        } else {
            if (this.subscription!=null) {
                resubscribeViolationAction.run();
            } else {
                this.subscription=VerifySubscription.of(subscription);
                subscriber.onSubscribe(subscription);
            }
        }
    }

    @Override
    public void onNext(T item) {
        if (!active.get()) {
            nextActiveViolationAction.run();
        } else {
            if (!subscription.registerInvocation()) {
                invalidInvocationCountAction.accept(subscription,item);
            }
            subscriber.onNext(item);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!active.compareAndSet(true,false)) {
            errorActiveViolationAction.run();
        } else {
            subscriber.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (!active.compareAndSet(true,false)) {
            completeActiveViolationAction.run();
        } else {
            subscriber.onComplete();
        }
    }
}
