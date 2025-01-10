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

package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Uni;
import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link Uni}.
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
public class Unis {
    /**
     * Create a uni from a completable-future.
     * @param future Completable-future.
     * @return Created uni.
     * @param <T> Type of item.
     */
    public static <T> Uni<T> fromCompletableFuture(CompletableFuture<T> future) {
        return Uni.createFrom().completionStage(future);
    }

    /**
     * Create a completable-future from a uni.
     * @param uni Uni.
     * @return Created completable-future.
     * @param <T> Type of item.
     */
    public static <T> CompletableFuture<T> toCompletableFuture(Uni<T> uni) {
        return uni.convert().toCompletableFuture();
    }

    /**
     * Create a uni from a publisher.
     * @param publisher Publisher.
     * @return Created uni.
     * @param <T> Type of item.
     */
    public static <T> Uni<T> fromPublisher(Flow.Publisher<T> publisher) {
        return Uni.createFrom().publisher(publisher);
    }

    /**
     * Create a publisher from a uni.
     * @param uni Uni.
     * @return Created publisher.
     * @param <T> Type of item.
     */
    public static <T> Flow.Publisher<T> toPublisher(Uni<T> uni) {
        return uni.convert().toPublisher();
    }
}
