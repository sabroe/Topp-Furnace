package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow;

public class SubscriberToStreamExample {
    public static void main(String[] args) {
        Flow.Subscriber<Integer> subscriber = new SubscriberToStream0<>();

        // Assuming you have a Publisher instance
        Flow.Publisher<Integer> publisher = new SimplePublisher0();
        publisher.subscribe(subscriber);

        // Cast back to access the stream functionality
        SubscriberToStream0<Integer> streamConverter = (SubscriberToStream0<Integer>) subscriber;

        // Use the stream
        streamConverter.stream()
                .forEach(item -> System.out.println("Item from stream: " + item));
    }
}
