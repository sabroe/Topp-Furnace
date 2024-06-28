package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow.*;

public class SimplePublisher implements Publisher<Integer> {
    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                new Thread(() -> {
                    try {
                        for (int i = 1; i <= 5; i++) {
                            subscriber.onNext(i);
                            Thread.sleep(1000); // Simulate delay between items
                        }
                        subscriber.onComplete();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                }).start();
            }

            @Override
            public void cancel() {
                // Handle cancellation
            }
        });
    }
}
