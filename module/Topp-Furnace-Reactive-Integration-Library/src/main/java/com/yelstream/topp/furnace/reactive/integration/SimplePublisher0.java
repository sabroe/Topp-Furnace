package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SimplePublisher0 implements Publisher<Integer> {
    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                for (int i = 1; i <= 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onComplete();
            }

            @Override
            public void cancel() {
                // Handle cancellation
            }
        });
    }
}
