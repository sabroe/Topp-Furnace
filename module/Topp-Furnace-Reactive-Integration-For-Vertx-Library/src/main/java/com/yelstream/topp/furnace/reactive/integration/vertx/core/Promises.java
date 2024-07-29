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

package com.yelstream.topp.furnace.reactive.integration.vertx.core;

import io.vertx.core.Promise;

import java.util.concurrent.CompletionStage;

/**
 * Utility addressing instances of {@link Promise}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
public class Promises {
    /**
     * Create a promise from a completion-stage.
     * @param completionStage Completion-stage.
     * @return Created promise.
     * @param <T> Type of item.
     */
    public static <T> Promise<T> fromCompletionStage(CompletionStage<T> completionStage) {
        Promise<T> promise=Promise.promise();
        completionStage.whenComplete((result,throwable) -> {
            if (throwable!=null) {
                promise.fail(throwable);
            } else {
                promise.complete(result);
            }
        });
        return promise;
    }

    /**
     * Create a completion-state from a promise.
     * @param promise Promise.
     * @return Created completion-stage.
     * @param <T> Type of item.
     */
    public static <T> CompletionStage<T> toCompletionStage(Promise<T> promise) {
        return promise.future().toCompletionStage();
    }
}
