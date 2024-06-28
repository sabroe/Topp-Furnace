package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow.*;

public class SubscriberExample {
    public static void main(String[] args) {
        // Example converting CompletionStage to Flow.Subscriber
        CompletionStage<String> completionStage = CompletableFuture.completedFuture("Hello, World!");

        CompletionStageToSubscriber<String> subscriber = new CompletionStageToSubscriber<>();
        completionStage.whenComplete((result, error) -> {
            if (error != null) {
                subscriber.onError(error);
            } else {
                subscriber.onNext(result);
                subscriber.onComplete();
            }
        });

        // Example converting Flow.Subscriber to CompletionStage
        SubscriberToCompletionStage<String> converter = new SubscriberToCompletionStage<>();
        completionStage = converter.getCompletionStage();
        completionStage.thenAccept(result -> {
            System.out.println("Received result: " + result);
        }).exceptionally(ex -> {
            System.err.println("Error: " + ex.getMessage());
            return null;
        });
    }
}
