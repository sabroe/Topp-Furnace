package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow;

public class SubscriberToIterableExample0 {
    public static void main(String[] args) {
        Flow.Subscriber<Integer> subscriber = new SubscriberToIterable0<>();

        // Assuming you have a Publisher instance
        Flow.Publisher<Integer> publisher = new SimplePublisher0();
        publisher.subscribe(subscriber);

        // Cast back to access the iterable functionality
        Iterable<Integer> iterable = (SubscriberToIterable0<Integer>) subscriber;

        // Iterate over the items
        for (Integer item : iterable) {
            System.out.println("Item from iterable: " + item);
        }
    }
}
