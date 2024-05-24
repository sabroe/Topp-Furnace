package com.yelstream.topp.execution.furnace.simple;

import java.time.Duration;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

public class Main {
    public static void main(String[] args) {
        // Create a publisher
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // Create a processor
        ComputationProcessor processor = new ComputationProcessor();

        // Subscribe the processor to the publisher
        publisher.subscribe(processor);

        // Create a subscriber
        Subscriber<Integer> subscriber = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1); // Request the first element
            }

            @Override
            public void onNext(Integer item) {
                // Process the received item
                System.out.println("Received: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle errors if any
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Processing complete");
            }
        };

        // Subscribe the subscriber to the processor
        processor.subscribe(subscriber);

        // Publish some items
        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);

try {
    Thread.sleep(Duration.ofSeconds(3));
} catch (InterruptedException ex) {
}

        // Close the publisher (to indicate that no more items will be published)
        publisher.close();
    }
}
