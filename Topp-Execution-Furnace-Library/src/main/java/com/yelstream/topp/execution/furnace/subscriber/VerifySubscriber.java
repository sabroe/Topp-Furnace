package com.yelstream.topp.execution.furnace.subscriber;

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
            run(subscribeActiveViolationAction);
        } else {
            if (this.subscription!=null) {
                run(resubscribeViolationAction);
            } else {
                this.subscription=VerifySubscription.of(subscription);
                subscriber.onSubscribe(subscription);
            }
        }
    }

    @Override
    public void onNext(T item) {
        if (!active.get()) {
            run(nextActiveViolationAction);
        } else {
            if (!subscription.registerInvocation()) {
                run(invalidInvocationCountAction);
            }
            subscriber.onNext(item);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!active.compareAndSet(true,false)) {
            run(errorActiveViolationAction);
        } else {
            subscriber.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (!active.compareAndSet(true,false)) {
            run(completeActiveViolationAction);
        } else {
            subscriber.onComplete();
        }
    }

    public static void run(Runnable runnable) {  //TODO: Use version in Standard Runnables!
        if (runnable!=null) {
            runnable.run();
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
