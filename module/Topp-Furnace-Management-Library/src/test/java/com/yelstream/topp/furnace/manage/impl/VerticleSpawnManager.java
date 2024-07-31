package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.SpawnManager;
import com.yelstream.topp.furnace.manage.Stoppable;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.concurrent.CompletableFuture;

public class VerticleSpawnManager implements SpawnManager<VerticleManager, Void, Exception> {

    private final Vertx vertx;
    private final Verticle verticle;
    private VerticleManager verticleManager;

    public VerticleSpawnManager(Vertx vertx, Verticle verticle) {
        this.vertx = vertx;
        this.verticle = verticle;
    }

    @Override
    public CompletableFuture<VerticleManager> start() throws Exception {
        CompletableFuture<VerticleManager> future = new CompletableFuture<>();
        vertx.deployVerticle(verticle, res -> {
            if (res.succeeded()) {
                String deploymentId = res.result();
                verticleManager = new VerticleManager(vertx, deploymentId);
                future.complete(verticleManager);
            } else {
                future.completeExceptionally(res.cause());
            }
        });
        return future;
    }

    @Override
    public void close() throws Exception {
        if (verticleManager != null) {
            try {
                verticleManager.stop().join();
            } catch (Exception ex) {
                throw new Exception("Failed to undeploy verticle", ex);
            }
        }
    }
}

class VerticleManager implements Stoppable<Void, Exception> {

    private final Vertx vertx;
    private final String deploymentId;

    public VerticleManager(Vertx vertx, String deploymentId) {
        this.vertx = vertx;
        this.deploymentId = deploymentId;
    }

    @Override
    public CompletableFuture<Void> stop() throws Exception {
        CompletableFuture<Void> future = new CompletableFuture<>();
        vertx.undeploy(deploymentId, res -> {
            if (res.succeeded()) {
                future.complete(null);
            } else {
                future.completeExceptionally(res.cause());
            }
        });
        return future;
    }
}
