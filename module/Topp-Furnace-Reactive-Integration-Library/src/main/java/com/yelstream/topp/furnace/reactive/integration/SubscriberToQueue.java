package com.yelstream.topp.furnace.reactive.integration;

import java.util.Queue;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubscriberToQueue<T> implements Flow.Subscriber<T> {

    private final Queue<T> queue;
    private Flow.Subscription subscription;
    private final AtomicBoolean completed = new AtomicBoolean(false);

    public SubscriberToQueue(Queue<T> queue) {
        this.queue = queue;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE); // Request all items
    }

    @Override
    public void onNext(T item) {
        queue.offer(item); // Non-blocking put
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace(); // Handle error appropriately
        completed.set(true);
    }

    @Override
    public void onComplete() {
        completed.set(true);
    }

    public boolean isCompleted() {
        return completed.get();
    }
}
