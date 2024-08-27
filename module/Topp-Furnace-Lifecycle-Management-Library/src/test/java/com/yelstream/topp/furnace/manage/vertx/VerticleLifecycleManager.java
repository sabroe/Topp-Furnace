package com.yelstream.topp.furnace.manage.vertx;

import com.yelstream.topp.furnace.manage.LifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

public class VerticleLifecycleManager implements LifecycleManager<Verticle,String,Exception> {

    @Getter
    private final Vertx vertx;

    @Getter
    private final Verticle verticle;

    @Getter
    private String deploymentId;

    @lombok.Builder(builderClassName="Builder",access=AccessLevel.PRIVATE)
    private VerticleLifecycleManager(Vertx vertx,
                                     Verticle verticle) {
        this.vertx=vertx;
        this.verticle=verticle;
    }

    @Override
    public CompletableFuture<Verticle> start() throws Exception {
        CompletableFuture<Verticle> future=new CompletableFuture<>();
        if (deploymentId!=null) {
            IllegalStateException ex=
                new IllegalStateException(String.format("Failure to start; Verticle is already deployed, previous deployment has id %s!",deploymentId));
            future.completeExceptionally(ex);
        } else {
            vertx.deployVerticle(verticle,res -> {
                if (res.failed()) {
                    future.completeExceptionally(res.cause());
                } else {
                    deploymentId=res.result();
                    future.complete(verticle);
                }
            });
        }
        return future;
    }

    @Override
    public CompletableFuture<String> stop() throws Exception {
        CompletableFuture<String> future=new CompletableFuture<>();
        if (deploymentId==null) {
            future.complete(null);
        } else {
            vertx.undeploy(deploymentId,res -> {
                if (res.failed()) {
                    future.completeExceptionally(res.cause());
                } else {
                    future.complete(deploymentId);
                }
            });
        }
        return future;
    }

    public static VerticleLifecycleManager of(Verticle verticle) {
        return builder().verticle(verticle).build();
    }

    public static VerticleLifecycleManager of(Vertx vertx,
                                              Verticle verticle) {
        return builder().vertx(vertx).verticle(verticle).build();
    }

    private static class Builder {
        public VerticleLifecycleManager build() {
            if (vertx==null) {
                vertx=verticle.getVertx();
            }
            return new VerticleLifecycleManager(vertx,verticle);
        }
    }
}
