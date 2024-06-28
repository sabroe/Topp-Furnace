package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow.*;

public class StreamPublisherExample {
    public static void main(String[] args) {
        java.util.stream.Stream<Integer> stream = java.util.stream.Stream.of(1, 2, 3, 4, 5);

        Publisher<Integer> publisher = new StreamPublisher<>(stream);

        publisher.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(5); // Request all items
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Received item: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
    }
}
