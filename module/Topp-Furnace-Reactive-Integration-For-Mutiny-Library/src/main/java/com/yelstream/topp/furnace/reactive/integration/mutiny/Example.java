package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Uni;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Example {
    public static void main(String[] args) {
        // Convert CompletionStage to Uni
        CompletionStage<String> completionStage = CompletableFuture.completedFuture("Hello from CompletionStage");
        Uni<String> uniFromCompletionStage = CompletionStageToUni.fromCompletionStage(completionStage);
        uniFromCompletionStage.subscribe().with(item -> System.out.println("Received from Uni: " + item));

        // Convert Uni to CompletionStage
        Uni<String> uni = Uni.createFrom().item("Hello from Uni");
        CompletionStage<String> completionStageFromUni = UniToCompletionStage.fromUni(uni);
        try {
            String result = completionStageFromUni.toCompletableFuture().get();
            System.out.println("Received from CompletionStage: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
