package com.yelstream.topp.furnace.vertx.core.xxx;

import com.yelstream.topp.furnace.vertx.core.LifecycleAwareVerticle;
import com.yelstream.topp.furnace.vertx.core.LifecycleListener;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        LifecycleListener listener = new MyLifecycleListener();
        MyCustomVerticle myVerticle = new MyCustomVerticle();
        LifecycleAwareVerticle lifecycleAwareVerticle = new LifecycleAwareVerticle(myVerticle, listener);
        vertx.deployVerticle(lifecycleAwareVerticle, res -> {
            if (res.succeeded()) {
                System.out.println("Deployment id is: " + res.result());
            } else {
                System.out.println("Deployment failed: " + res.cause());
            }
        });
    }
}
