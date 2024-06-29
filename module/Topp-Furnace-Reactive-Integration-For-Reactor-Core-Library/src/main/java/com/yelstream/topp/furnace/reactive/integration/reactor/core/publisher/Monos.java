/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.furnace.reactive.integration.reactor.core.publisher;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Utility addressing instances of {@link Mono}.
 * <p>
 *     This contains conversions to and from {@link CompletableFuture} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
@UtilityClass
public class Monos {
    /**
     * Create a mono from a completable-future.
     * @param future Completable-future.
     * @return Created mono.
     * @param <T> Type of item.
     */
    public static <T> Mono<T> fromCompletableFuture(CompletableFuture<T> future) {
        return Mono.fromFuture(future);
    }

    /**
     * Create a completable-future from a mono.
     * @param mono Mono.
     * @return Created completable-future.
     * @param <T> Type of item.
     */
    public static <T> CompletableFuture<T> toCompletableFuture(Mono<T> mono) {
        return mono.toFuture();
    }
}
