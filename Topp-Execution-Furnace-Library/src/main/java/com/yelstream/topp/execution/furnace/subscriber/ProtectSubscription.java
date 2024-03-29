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
    @Getter
    private final P subscription;

    private final AtomicBoolean active=new AtomicBoolean(true);

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
