package com.yelstream.topp.furnace.manage;

import java.util.concurrent.CompletableFuture;

public interface Startable<S, E extends Exception> {
    CompletableFuture<S> start() throws E;
}
