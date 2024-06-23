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

package com.yelstream.topp.execution.concurrent.future;

import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Function;

@UtilityClass
public class Futures {

    public static <T> CompletableFuture<T> toCompletableFuture(Future<T> future) {
        if (future instanceof CompletableFuture<T> completableFuture) {
            return completableFuture;
        } else {
            return createCompletableFuture(future);
        }
    }

    public static <T> CompletableFuture<T> toCompletableFuture(Future<T> future,
                                                               Executor executor) {
        if (future instanceof CompletableFuture<T> completableFuture) {
            return completableFuture;
        } else {
            return createCompletableFuture(future,executor);
        }
    }

    public static <T> CompletableFuture<T> createCompletableFuture(Future<T> future) {
        CompletableFuture<T> result=new CompletableFuture<>();
        if (future.isDone()) {
            completeResult(future,result);  //Block!
        } else {
            CompletableFuture.runAsync(()->completeResult(future,result));
        }
        return result;
    }

    public static <T> CompletableFuture<T> createCompletableFuture(Future<T> future,
                                                                   Executor executor) {
        CompletableFuture<T> result=new CompletableFuture<>();
        if (future.isDone()) {
            completeResult(future,result);  //Block!
        } else {
            CompletableFuture.runAsync(()->completeResult(future,result),executor);
        }
        return result;
    }

    public static <T,V> CompletableFuture<V> createCompletableFuture(CompletionStage<? extends T> stage,
                                                                     Function<T,Future<V>> consumer) {
        CompletableFuture<V> result=new CompletableFuture<>();
        stage.whenComplete((item,throwable) -> {
            if (throwable!=null) {
                result.completeExceptionally(throwable);
            } else {
                Future<V> future=consumer.apply(item);
                completeResult(future,result);  //Block!
            }
        });
        return result;
    }

    private static <T> void completeResult(Future<T> future,
                                          CompletableFuture<T> result) {
        try {
            T value=future.get();  //Yes, blocks!
            result.complete(value);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            result.completeExceptionally(ex);
        } catch (ExecutionException ex) {
            result.completeExceptionally(ex.getCause());
        }
    }

    public static <T> Future<T> fromCompletionStage(CompletionStage<T> completionStage) {
        if (completionStage instanceof CompletableFuture) {
            return (CompletableFuture<T>)completionStage;
        } else {
            CompletableFuture<T> completableFuture=new CompletableFuture<>();
            completionStage.whenComplete((result,error) -> {
                if (error!=null) {
                    completableFuture.completeExceptionally(error);
                } else {
                    completableFuture.complete(result);
                }
            });
            return completableFuture;
        }
    }
}
