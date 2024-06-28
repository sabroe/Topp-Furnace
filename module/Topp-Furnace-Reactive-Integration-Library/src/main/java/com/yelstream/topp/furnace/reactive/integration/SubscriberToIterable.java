package com.yelstream.topp.furnace.reactive.integration;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubscriberToIterable<T> implements Iterable<T>, Subscriber<T> {

    private final BlockingQueue<T> queue = new ArrayBlockingQueue<>(100);
    private volatile boolean completed = false;
    private volatile Throwable error = null;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE); // Request all items
    }

    @Override
    public void onNext(T item) {
        try {
            queue.put(item); // Block if the queue is full
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        error = throwable;
        completed = true;
    }

    @Override
    public void onComplete() {
        completed = true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                while (!completed || !queue.isEmpty()) {
                    if (!queue.isEmpty()) {
                        return true;
                    }
                    // Busy-wait until there is an item or the stream is completed
                    Thread.onSpinWait();
                }
                return false;
            }

            @Override
            public T next() {
                try {
                    while (true) {
                        T item = queue.poll();
                        if (item != null) {
                            return item;
                        }
                        if (completed && queue.isEmpty()) {
                            throw new java.util.NoSuchElementException();
                        }
                        // Busy-wait until there is an item
                        Thread.onSpinWait();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
