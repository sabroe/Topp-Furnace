package com.yelstream.topp.furnace.life.deploy.op;

import java.util.concurrent.CompletableFuture;

public interface Undeployable<S,E extends Exception> {
    CompletableFuture<S> undeploy() throws E;
}
