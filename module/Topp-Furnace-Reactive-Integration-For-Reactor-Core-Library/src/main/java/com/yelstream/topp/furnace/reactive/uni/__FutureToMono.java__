package com.yelstream.topp.furnace.reactive.uni;

import reactor.core.publisher.Mono;
import io.vertx.core.Future;

public class FutureToMono {
    public static <T> Mono<T> fromVertxFuture(Future<T> future) {
        return Mono.create(sink -> {
            future.onSuccess(sink::success)
                    .onFailure(sink::error);
        });
    }
}
