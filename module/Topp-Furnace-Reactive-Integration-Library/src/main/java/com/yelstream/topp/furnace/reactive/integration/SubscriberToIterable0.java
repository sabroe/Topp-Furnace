package com.yelstream.topp.furnace.reactive.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubscriberToIterable0<T> implements Iterable<T>, Subscriber<T> {

    private final List<T> items = new ArrayList<>();
    private boolean completed = false;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE); // Request all items
    }

    @Override
    public void onNext(T item) {
        items.add(item);
    }

    @Override
    public void onError(Throwable throwable) {
        // Handle error
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        completed = true;
    }

    @Override
    public Iterator<T> iterator() {
        while (!completed) {
            // Busy-wait until onComplete is called
            Thread.onSpinWait();
        }
        return items.iterator();
    }
}
