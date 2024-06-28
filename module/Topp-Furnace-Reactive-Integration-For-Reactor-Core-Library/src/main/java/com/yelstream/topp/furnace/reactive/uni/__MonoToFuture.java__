package com.yelstream.topp.furnace.reactive.uni;

import reactor.core.publisher.Mono;
import io.vertx.core.Promise;
import io.vertx.core.Future;

public class MonoToFuture {
    public static <T> Future<T> fromMono(Mono<T> mono) {
        Promise<T> promise = Promise.promise();
        mono.subscribe(promise::complete, promise::fail);
        return promise.future();
    }
}
