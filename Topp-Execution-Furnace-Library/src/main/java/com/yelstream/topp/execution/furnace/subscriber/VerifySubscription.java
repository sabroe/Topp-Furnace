package com.yelstream.topp.execution.furnace.subscriber;

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
            requestActiveViolationAction.run();
        } else {
            accumulatedRequestCount.addAndGet(n);
            remainingInvocationCount.addAndGet(n);
            subscription.request(n);
        }
    }

    @Override
    public void cancel() {
        if (!active.compareAndSet(true,false)) {
            cancelActiveViolationAction.run();
        } else {
            subscription.cancel();
        }
    }

    public static void run(Runnable runnable) {  //TODO: Use version in Standard Runnables!
        if (runnable!=null) {
            runnable.run();
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
