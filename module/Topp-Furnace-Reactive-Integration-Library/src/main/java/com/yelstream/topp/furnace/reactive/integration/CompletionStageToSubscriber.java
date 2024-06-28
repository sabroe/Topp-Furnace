package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;

public class CompletionStageToSubscriber<T> implements Subscriber<T> {

    private final CompletableFuture<Void> completion = new CompletableFuture<>();

    private T item;
    private Throwable error;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1); // Requesting only one item
    }

    @Override
    public void onNext(T item) {
        this.item = item; // Store the item
    }

    @Override
    public void onError(Throwable throwable) {
        this.error = throwable; // Store the error
        completion.completeExceptionally(throwable);
    }

    @Override
    public void onComplete() {
        completion.complete(null);
    }

    public CompletionStage<T> getCompletionStage() {
        return completion.thenApply(ignored -> {
            if (error != null) {
                throw new CompletionException(error);
            }
            return item;
        });
    }
}
