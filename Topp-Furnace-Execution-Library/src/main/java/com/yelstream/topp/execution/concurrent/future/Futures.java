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
}
