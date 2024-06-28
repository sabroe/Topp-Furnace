package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.concurrent.CompletionStage;

public class CompletionStageToFuture {
    public static <T> Future<T> fromCompletionStage(CompletionStage<T> stage) {
        Promise<T> promise = Promise.promise();
        stage.whenComplete((result, error) -> {
            if (error != null) {
                promise.fail(error);
            } else {
                promise.complete(result);
            }
        });
        return promise.future();
    }
}
