package com.yelstream.topp.furnace.reactive.integration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow;

public class SubscriberToQueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        SubscriberToQueue<Integer> subscriberToQueue = new SubscriberToQueue<>(queue);

        // Create a simple publisher
        Flow.Publisher<Integer> publisher = new SimplePublisher();
        publisher.subscribe(subscriberToQueue);

        // Process the queue in another thread
        new Thread(() -> {
            while (!subscriberToQueue.isCompleted() || !queue.isEmpty()) {
                Integer item = queue.poll();
                if (item != null) {
                    System.out.println("Item from queue: " + item);
                } else {
                    // Sleep for a while to avoid busy-waiting
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            System.out.println("Queue processing completed.");
        }).start();
    }
}
