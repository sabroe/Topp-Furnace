package com.yelstream.topp.furnace.reactive.integration;

import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class IterablePublisher<T> implements Publisher<T> {

    private final Iterable<T> iterable;

    public IterablePublisher(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        Iterator<T> iterator = iterable.iterator();
        subscriber.onSubscribe(new Subscription() {
            private boolean completed = false;

            @Override
            public void request(long n) {
                if (n <= 0) {
                    subscriber.onError(new IllegalArgumentException("Demand must be positive"));
                    return;
                }
                for (long i = 0; i < n && iterator.hasNext(); i++) {
                    subscriber.onNext(iterator.next());
                }
                if (!iterator.hasNext() && !completed) {
                    completed = true;
                    subscriber.onComplete();
                }
            }

            @Override
            public void cancel() {
                completed = true;
            }
        });
    }
}
