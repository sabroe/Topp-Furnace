package com.yelstream.topp.furnace.reactive.integration.reactor;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletionStage;

public class CompletionStageToMono {
    public static <T> Mono<T> fromCompletionStage(CompletionStage<T> stage) {
        return Mono.fromCompletionStage(stage);
    }
}

