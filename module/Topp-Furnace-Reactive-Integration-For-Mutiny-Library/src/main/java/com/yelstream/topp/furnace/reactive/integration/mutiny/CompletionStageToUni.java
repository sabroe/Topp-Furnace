package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Uni;

import java.util.concurrent.CompletionStage;

public class CompletionStageToUni {
    public static <T> Uni<T> fromCompletionStage(CompletionStage<T> completionStage) {
        return Uni.createFrom().completionStage(completionStage);
    }
}
