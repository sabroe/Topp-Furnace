package com.yelstream.topp.furnace.vertx.core.xxx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MyCustomVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        try {
            // Custom start logic
            vertx.setTimer(1000, id -> {
                // Simulate success or failure
                if (Math.random() > 0.5) {
                    startPromise.complete();
                } else {
                    throw new RuntimeException("Failed to start");
                }
            });
        } catch (Exception e) {
            startPromise.fail(e);
        }
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        try {
            // Custom shutdown logic
            vertx.setTimer(500, id -> stopPromise.complete());
        } catch (Exception e) {
            // Handle any exception during shutdown
            stopPromise.fail(e); // Fail the promise if an exception occurs
        }
    }
}
