/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.furnace.manage.vertx;

import com.yelstream.topp.furnace.life.manage.AbstractManageable;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

/**
 * Manageable implementation for a Vert.x Verticle.
 *
 * @version 1.0
 * @since 2024-07-29
 */
public class VerticleManageable extends AbstractManageable<String,Void,RuntimeException,VerticleLifecycleManager> {

    @Getter
    private final Vertx vertx;  //TO-DO: Consider Supplier<Vertx>!

    @Getter
    private final Verticle verticle;  //TO-DO: Consider Supplier<Verticle>!

    @Getter
    private String deploymentId;

    @lombok.Builder(builderClassName="Builder",access=AccessLevel.PRIVATE)
    private VerticleManageable(Vertx vertx,
                               Verticle verticle) {
        if (vertx==null) {
            vertx=verticle.getVertx();
        }
        this.vertx=vertx;
        this.verticle=verticle;
    }

    @Override
    protected VerticleLifecycleManager createManager() {
        return new VerticleLifecycleManager(this::start,this::stop);
    }

    private CompletableFuture<String> start() {
        CompletableFuture<String> future=new CompletableFuture<>();
        if (deploymentId!=null) {  //TO-DO: Consider moving this test for "is already deployed" to the AbstractManager! --Will reduce these lines of code !
            IllegalStateException ex=
                new IllegalStateException(String.format("Failure to start; Verticle is already deployed, previous deployment has id %s!", deploymentId));
            future.completeExceptionally(ex);
        } else {
            vertx.deployVerticle(verticle, res -> {
                if (res.failed()) {
                    future.completeExceptionally(res.cause());
                } else {
                    deploymentId=res.result();
                    future.complete(deploymentId);
                }
            });
        }
        return future;
    }

    private CompletableFuture<Void> stop() {
        CompletableFuture<Void> future=new CompletableFuture<>();
        if (deploymentId==null) {  //TO-DO: Consider moving this test for "is already deployed" to the AbstractManager! --Will reduce these lines of code!
            future.complete(null);
        } else {
            vertx.undeploy(deploymentId,res -> {
                if (res.failed()) {
                    future.completeExceptionally(res.cause());
                } else {
                    deploymentId=null;
                    future.complete(null);
                }
            });
        }
        return future;
    }

    public static VerticleManageable of(Verticle verticle) {
        return builder().verticle(verticle).build();
    }

    public static VerticleManageable of(Vertx vertx,
                                        Verticle verticle) {
        return builder().vertx(vertx).verticle(verticle).build();
    }
}
