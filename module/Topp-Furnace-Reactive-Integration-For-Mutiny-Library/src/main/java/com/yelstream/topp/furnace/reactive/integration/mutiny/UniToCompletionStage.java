package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Uni;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UniToCompletionStage {
    public static <T> CompletionStage<T> fromUni(Uni<T> uni) {
        CompletableFuture<T> future = new CompletableFuture<>();
        uni.subscribe().with(future::complete, future::completeExceptionally);
        return future;
    }
}
