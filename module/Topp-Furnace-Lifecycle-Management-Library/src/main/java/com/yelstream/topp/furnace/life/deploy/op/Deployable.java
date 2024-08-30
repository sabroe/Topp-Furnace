package com.yelstream.topp.furnace.life.deploy.op;

import java.util.concurrent.CompletableFuture;

public interface Deployable<S,E extends Exception> {
    CompletableFuture<S> deploy() throws E;
}
