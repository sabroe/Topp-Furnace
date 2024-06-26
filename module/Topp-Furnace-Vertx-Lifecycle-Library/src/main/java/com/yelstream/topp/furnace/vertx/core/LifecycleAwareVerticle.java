package com.yelstream.topp.furnace.vertx.core;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;

public class LifecycleAwareVerticle extends ProxyVerticle {

    private final LifecycleListener lifecycleListener;

    public LifecycleAwareVerticle(Verticle delegate, LifecycleListener lifecycleListener) {
        super(delegate);
        this.lifecycleListener = lifecycleListener;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        lifecycleListener.onStartCalled();
        Promise<Void> internalPromise = Promise.promise();
        super.start(internalPromise);

        internalPromise.future().onComplete(ar -> {
            if (ar.succeeded()) {
                lifecycleListener.onStartCompleted();
                startPromise.complete();
            } else {
                lifecycleListener.onStartFailed(ar.cause());
                startPromise.fail(ar.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        lifecycleListener.onStopCalled();
        Promise<Void> internalPromise = Promise.promise();
        super.stop(internalPromise);

        internalPromise.future().onComplete(ar -> {
            if (ar.succeeded()) {
                lifecycleListener.onStopCompleted();
                stopPromise.complete();
            } else {
                lifecycleListener.onStopFailed(ar.cause());
                stopPromise.fail(ar.cause());
            }
        });
    }
}
