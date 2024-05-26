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

package com.yelstream.topp.furnace.vertx.core;

import io.vertx.core.Future;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * Utility addressing instances of Vert.x {@link Future}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@UtilityClass
public class Futures {
    public static <X> List<CompletableFuture<X>> toCompletableFuture(List<Future<X>> futures) {
        return futures==null?null:futures.stream().filter(Objects::nonNull).map(Future::toCompletionStage).map(CompletionStage::toCompletableFuture).toList();
    }

    public static <X> List<Future<X>> toFuture(List<Supplier<Future<X>>> futureSuppliers) {
        return futureSuppliers==null?null:futureSuppliers.stream().filter(Objects::nonNull).map(Supplier::get).toList();
    }
}
