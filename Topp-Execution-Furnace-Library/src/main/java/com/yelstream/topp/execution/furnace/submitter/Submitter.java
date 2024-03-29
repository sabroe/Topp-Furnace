package com.yelstream.topp.execution.furnace.submitter;

public interface Submitter<T,V> {

    void submit(T item);

//    CompletableFuture<V> submit(T item);
}
