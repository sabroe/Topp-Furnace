package com.yelstream.topp.execution.furnace.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class BaseSubscription<P extends Flow.Subscription> implements Flow.Subscription {
    @Getter
    private final P subscription;

    private AtomicBoolean alive=new AtomicBoolean();

    private AtomicLong requestCount=new AtomicLong();

    public long getRequestCount() {
        return requestCount.get();
    }

    @Override
    public void request(long n) {
        if (alive.get()) {
            subscription.request(n);
            requestCount.addAndGet(n);
        }
    }

    @Override
    public void cancel() {
        if (alive.compareAndSet(true,false)) {
            subscription.cancel();
        }
    }
}
