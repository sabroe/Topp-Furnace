package com.yelstream.topp.furnace.reactive.integration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class QueuePublisherExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        QueuePublisher<Integer> queuePublisher = new QueuePublisher<>(queue);

        queuePublisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Received: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });

        // Start the publisher
        queuePublisher.start();

        // Add items to the queue
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                queue.add(i);
                try {
                    Thread.sleep(500); // Simulate delay between items
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
