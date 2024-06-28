package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FutureToCompletionStage {
    public static <T> CompletionStage<T> fromFuture(Future<T> future) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        future.onSuccess(completableFuture::complete).onFailure(completableFuture::completeExceptionally);
        return completableFuture;
    }
}
