package com.yelstream.topp.furnace.reactive.integration.vertx.core;

import io.vertx.core.Promise;

import java.util.concurrent.CompletionStage;

public class Promises {


    public static <T> Promise<T> fromCompletionStage(CompletionStage<T> completionStage) {
        Promise<T> promise = Promise.promise();
        completionStage.whenComplete((result, throwable) -> {
            if (throwable != null) {
                promise.fail(throwable);
            } else {
                promise.complete(result);
            }
        });
        return promise;
    }

    public static <T> CompletionStage<T> toCompletionStage(Promise<T> promise) {
        return promise.future().toCompletionStage();
    }

}
