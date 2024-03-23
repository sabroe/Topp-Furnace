package com.yelstream.topp.execution.furnace.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class BaseSubscriber<T, S extends Flow.Subscriber<T>> implements Flow.Subscriber<T> {
    @Getter
    private final S subscriber;

    private AtomicBoolean alive=new AtomicBoolean();

    public void onSubscribe(Flow.Subscription subscription) {
        if (alive.get()) {
            subscriber.onSubscribe(subscription);
        }
    }

    public void onNext(T item) {
        if (alive.get()) {
            subscriber.onNext(item);
        }
    }

    public void onError(Throwable throwable) {
        if (alive.get()) {
            subscriber.onError(throwable);
        }
    }

    public void onComplete() {
        if (alive.get()) {
            subscriber.onComplete();
        }
    }
}
