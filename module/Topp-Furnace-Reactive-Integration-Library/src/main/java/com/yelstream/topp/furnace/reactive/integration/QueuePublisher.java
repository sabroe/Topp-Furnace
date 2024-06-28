package com.yelstream.topp.furnace.reactive.integration;

import java.util.Queue;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class QueuePublisher<T> implements Flow.Publisher<T> {
    private final Queue<T> queue;
    private final SubmissionPublisher<T> publisher;

    public QueuePublisher(Queue<T> queue) {
        this.queue = queue;
        this.publisher = new SubmissionPublisher<>();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(subscriber);
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                T item;
                synchronized (queue) {
                    item = queue.poll();
                }
                if (item != null) {
                    publisher.submit(item);
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
        }).start();
    }

    public void stop() {
        publisher.close();
    }
}
