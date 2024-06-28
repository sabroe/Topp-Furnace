package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow;

public class SubscriberToIterableExample {
    public static void main(String[] args) {
        Flow.Subscriber<Integer> subscriber = new SubscriberToIterable<>();

        // Assuming you have a Publisher instance
        Flow.Publisher<Integer> publisher = new SimplePublisher();
        publisher.subscribe(subscriber);

        // Cast back to access the iterable functionality
        Iterable<Integer> iterable = (SubscriberToIterable<Integer>) subscriber;

        // Iterate over the items as they arrive
        for (Integer item : iterable) {
            System.out.println("Item from iterable: " + item);
        }
    }
}
