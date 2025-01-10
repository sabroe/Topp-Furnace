/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.furnace.reactive.integration.vertx.core;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link Future}.
 * <p>
 *     This contains conversions to and from {@link CompletableFuture} instances.
 * </p>
 * <p>
 *     This contains conversions to and from {@link Flow.Publisher} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
@UtilityClass
public class Futures {
    /**
     * Create a future from a completable-future.
     * @param future Completable-future.
     * @return Created future.
     * @param <T> Type of item.
     */
    public static <T> Future<T> fromCompletableFuture(CompletableFuture<T> future) {
        return Future.fromCompletionStage(future);
    }

    /**
     * Create a completable-future from a future.
     * @param future Future.
     * @return Created completable-future.
     * @param <T> Type of item.
     */
    public static <T> CompletableFuture<T> toCompletableFuture(Future<T> future) {
        return future.toCompletionStage().toCompletableFuture();
    }

    /**
     * Create a future from a publisher.
     * @param publisher Publisher.
     * @return Created future.
     * @param <T> Type of item.
     */
    public static <T> Future<T> fromPublisher(Flow.Publisher<T> publisher) {
        Promise<T> promise = Promise.promise();
        publisher.subscribe(new Flow.Subscriber<T>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription s) {
                this.subscription = s;
                s.request(1); // Request initial item
            }

            @Override
            public void onNext(T item) {
                promise.complete(item);
                subscription.cancel(); // Cancel after completing the promise
            }

            @Override
            public void onError(Throwable t) {
                promise.fail(t);
            }

            @Override
            public void onComplete() {
                // Do nothing here since we complete on onNext
            }
        });
        return promise.future();
    }

    /**
     * Create a publisher from a future.
     * @param future Future.
     * @return Created publisher.
     * @param <T> Type of item.
     */    public static <T> Flow.Publisher<T> toPublisher(Future<T> future) {
        return new Flow.Publisher<T>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> subscriber) {
                future.onComplete(result -> {
                    if (result.succeeded()) {
                        subscriber.onNext(result.result());
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(result.cause());
                    }
                });
            }
        };
    }
}
