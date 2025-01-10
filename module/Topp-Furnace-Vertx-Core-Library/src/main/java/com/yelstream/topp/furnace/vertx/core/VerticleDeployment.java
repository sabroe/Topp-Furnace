/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.furnace.vertx.core;

import com.yelstream.topp.furnace.life.deploy.Deployment;
import com.yelstream.topp.furnace.life.deploy.op.Undeployable;
import com.yelstream.topp.furnace.vertx.core.function.VerticleUndeploymentFunction;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class VerticleDeployment<V extends Verticle> implements Deployment<V,RuntimeException> {

    private final Vertx vertx;

    private final V verticle;

    private final DeploymentOptions deploymentOptions;

    private final String deploymentId;

    /**
     *
     */
    private final UndeployableFunction<V> undeployableFunction;

    @FunctionalInterface
    private interface UndeployableFunction<V extends Verticle> {
        Undeployable<V,RuntimeException> createUndeployable(Vertx vertx, V verticle, DeploymentOptions deploymentOptions, String deploymentId);
    }

    @Override
    public CompletableFuture<V> undeploy() {
        Undeployable<V,RuntimeException> undeployAction=undeployableFunction.createUndeployable(vertx,verticle,deploymentOptions,deploymentId);
        return undeployAction.undeploy();
    }

    public static class Builder<V extends Verticle> {

        private Vertx vertx;

        private V verticle;

        private DeploymentOptions deploymentOptions;

        private String deploymentId;

        private UndeployableFunction<V> undeployableFunction;

        private VerticleUndeploymentFunction undeploymentFunction;

        public Builder<V> undeploymentFunction(VerticleUndeploymentFunction undeploymentFunction) {
            this.undeploymentFunction=undeploymentFunction;
            return this;
        }

        public VerticleDeployment<V> build() {
            if (undeploymentFunction==null) {
                undeploymentFunction=VerticleUndeploymentFunction.DEFAULT;
            }
            if (undeployableFunction==null) {
                undeployableFunction=(vertx,verticle,deploymentOptions,deploymentId)->VerticleDeployment.createUndeployAction(vertx,verticle,deploymentId,undeploymentFunction);
            }
            return new VerticleDeployment<>(vertx,verticle,deploymentOptions,deploymentId,undeployableFunction);
        }
    }

    private static <V extends Verticle> Undeployable<V,RuntimeException> createUndeployAction(Vertx vertx,
                                                                                              V verticle,
                                                                                              String deploymentId,
                                                                                              VerticleUndeploymentFunction undeploymentFunction) {
        return ()->{
            Future<Void> future=undeploymentFunction.deploy(vertx,deploymentId);
            return Futures.toCompletableFuture(future,v->verticle);
        };
    }
}
