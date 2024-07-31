package com.yelstream.topp.furnace.manage;

import java.util.concurrent.CompletableFuture;

public interface Stoppable<T, E extends Exception> {
    CompletableFuture<T> stop() throws E;
}
