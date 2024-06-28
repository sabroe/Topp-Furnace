package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;

public class SubscriberToCompletionStage<T> {

    private CompletableFuture<T> future = new CompletableFuture<>();

    private Subscriber<T> subscriber = new Subscriber<T>() {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1); // Requesting only one item
        }

        @Override
        public void onNext(T item) {
            future.complete(item); // Complete the CompletableFuture with the item
        }

        @Override
        public void onError(Throwable throwable) {
            future.completeExceptionally(throwable); // Complete exceptionally on error
        }

        @Override
        public void onComplete() {
            if (!future.isDone()) {
                future.completeExceptionally(new IllegalStateException("Flow.Subscriber completed without emitting an item"));
            }
        }
    };

    public Subscriber<T> getSubscriber() {
        return subscriber;
    }

    public CompletionStage<T> getCompletionStage() {
        return future;
    }
}
