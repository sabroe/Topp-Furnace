package com.yelstream.topp.furnace.vertx.core;

import io.vertx.core.Verticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.Context;

public class ProxyVerticle implements Verticle {

    private final Verticle delegate;

    public ProxyVerticle(Verticle delegate) {
        this.delegate = delegate;
    }

    @Override
    public Vertx getVertx() {
        return delegate.getVertx();
    }

    @Override
    public void init(Vertx vertx, Context context) {
        delegate.init(vertx, context);
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        delegate.start(startPromise);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        delegate.stop(stopPromise);
    }
}
