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
import io.vertx.core.Promise;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Promise}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@UtilityClass
public class Promises {

    public static <X> void tiePromise(Promise<X> promise,
                                      CompletableFuture<X> future) {
        if (promise!=null) {  //TO-DO: Consider this line!
            if (future==null) {
                promise.complete();
            } else {
                future.handle((result,throwable) -> {
                    if (throwable!=null) {
                        promise.fail(throwable);
                    } else {
                        promise.complete();
                    }
                    return result;
                });
            }
        }
    }

    public static void stop(Promise<Void> stopPromise,
                            List<Supplier<Future<Void>>> futureSuppliers) {
        List<CompletableFuture<Void>> futures=
            Futures.toCompletableFuture(Futures.toFuture(futureSuppliers));

        CompletableFuture<Void> future=allOf(futures);

        tiePromise(stopPromise,future);
    }

    @SafeVarargs
    public static void stop(Promise<Void> stopPromise,
                            Supplier<Future<Void>>... futureSuppliers) {
        stop(stopPromise,List.of(futureSuppliers));
    }

    public static void stop2(Promise<Void> stopPromise,
                            List<Supplier<CompletionStage<Void>>> stageSuppliers) {
        List<CompletionStage<Void>> stages=
            stageSuppliers.stream().map(Supplier::get).toList();

        CompletableFuture<Void> future=_allOf(stages);

        tiePromise(stopPromise,future);
    }

    public static CompletableFuture<Void> _allOf(List<CompletionStage<Void>> stages) {
        CompletableFuture<?>[] futureArray=stages.stream().map(CompletionStage::toCompletableFuture).toArray(CompletableFuture<?>[]::new);
        return CompletableFuture.allOf(futureArray);
    }

    public static CompletableFuture<Void> allOf(List<CompletableFuture<Void>> futures) {
        CompletableFuture<?>[] futureArray=futures.toArray(new CompletableFuture<?>[0]);
        return CompletableFuture.allOf(futureArray);
    }

    public static CompletableFuture<Object> anyOf(List<CompletableFuture<?>> futures) {
        CompletableFuture<?>[] futureArray=futures.toArray(new CompletableFuture<?>[0]);
        return CompletableFuture.anyOf(futureArray);
    }
}
