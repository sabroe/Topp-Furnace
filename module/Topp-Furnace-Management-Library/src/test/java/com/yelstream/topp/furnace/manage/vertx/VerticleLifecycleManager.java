package com.yelstream.topp.furnace.manage.vertx;

import com.yelstream.topp.furnace.manage.LifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.concurrent.CompletableFuture;

public class VerticleLifecycleManager implements LifecycleManager<Verticle,Void,Exception> {

    private final Vertx vertx;
    private final Verticle verticle;
    private String deploymentId;

    public VerticleLifecycleManager(Vertx vertx, Verticle verticle) {
        this.vertx = vertx;
        this.verticle = verticle;
    }

    @Override
    public CompletableFuture<Verticle> start() throws Exception {
        CompletableFuture<Verticle> future = new CompletableFuture<>();
        vertx.deployVerticle(verticle, res -> {
            if (res.succeeded()) {
                deploymentId = res.result();
                future.complete(verticle);
            } else {
                future.completeExceptionally(res.cause());
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<Void> stop() throws Exception {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (deploymentId != null) {
            vertx.undeploy(deploymentId, res -> {
                if (res.succeeded()) {
                    future.complete(null);
                } else {
                    future.completeExceptionally(res.cause());
                }
            });
        } else {
            future.complete(null); // Verticle was not deployed
        }
        return future;
    }

    @Override
    public void close() throws Exception {
        try {
            stop().join();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close lifecycle manager!", ex);
        }
    }
}
