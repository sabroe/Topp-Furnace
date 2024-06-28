package com.yelstream.topp.furnace.reactive.integration.reactor;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MonoToCompletionStage {
    public static <T> CompletionStage<T> fromMono(Mono<T> mono) {
        CompletableFuture<T> future = new CompletableFuture<>();
        mono.subscribe(future::complete, future::completeExceptionally);
        return future;
    }
}