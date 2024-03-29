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
@lombok.Builder(builderClassName="Builder")
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
