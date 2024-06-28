package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FlowPublisherToCompletionStage<T> {

    public CompletionStage<T> toCompletionStage(Flow.Publisher<T> publisher) {
        CompletableFuture<T> future = new CompletableFuture<>();

        publisher.subscribe(new Flow.Subscriber<T>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1); // Request the single item
            }

            @Override
            public void onNext(T item) {
                future.complete(item); // Complete the CompletableFuture with the item
                subscription.cancel(); // Cancel subscription after receiving the item
            }

            @Override
            public void onError(Throwable throwable) {
                future.completeExceptionally(throwable); // Complete exceptionally on error
            }

            @Override
            public void onComplete() {
                if (!future.isDone()) {
                    future.completeExceptionally(new IllegalStateException("Flow.Publisher completed without emitting an item"));
                }
            }
        });

        return future;
    }
}
