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

package com.yelstream.topp.furnace.vertx.core;

import com.yelstream.topp.furnace.life.deploy.DeploymentDescriptor;
import com.yelstream.topp.furnace.life.deploy.op.Deployable;
import com.yelstream.topp.furnace.vertx.core.function.VerticleDeploymentFunction;
import com.yelstream.topp.furnace.vertx.core.function.VerticleUndeploymentFunction;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Associates the creation of a Verticle together with its deployment-options.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class VerticleDeploymentDescriptor<V extends Verticle> implements DeploymentDescriptor<V,VerticleDeployment<V>,RuntimeException> {
    /**
     * Creator of Vertx.
     */
    private final Supplier<Vertx> vertxSupplier;

    /**
     * Creator of Verticle.
     */
    private final Supplier<V> verticleSupplier;

    /**
     * Creator of deployment-options.
     */
    private final Supplier<DeploymentOptions> deploymentOptionsSupplier;

    /**
     *
     */
    private final DeployableFunction<V> deployableFunction;

    @FunctionalInterface
    private interface DeployableFunction<V extends Verticle> {
        Deployable<VerticleDeployment<V>,RuntimeException> createDeployable(Supplier<Vertx> vertxSupplier, Supplier<V> verticleSupplier, Supplier<DeploymentOptions> deploymentOptionsSupplier);
    }

    @Override
    public CompletableFuture<VerticleDeployment<V>> deploy() {
        Deployable<VerticleDeployment<V>,RuntimeException> deployAction=deployableFunction.createDeployable(vertxSupplier,verticleSupplier,deploymentOptionsSupplier);
        return deployAction.deploy();
    }

    public static class Builder<V extends Verticle> {
        private Supplier<Vertx> vertxSupplier;

        private Supplier<V> verticleSupplier;

        private Supplier<DeploymentOptions> deploymentOptionsSupplier;

        private DeployableFunction<V> deployableFunction;

        private VerticleDeploymentFunction deploymentFunction;

        private VerticleUndeploymentFunction undeploymentFunction;

        public Builder<V> vertx(Vertx vertx) {
            return vertxSupplier(()->vertx);
        }

        public Builder<V> verticle(V verticle) {
            return verticleSupplier(()->verticle);
        }

        public Builder<V> deploymentOptions(DeploymentOptions deploymentOptions) {
            return deploymentOptionsSupplier(()->deploymentOptions);
        }

        public Builder<V> deploymentFunction(VerticleDeploymentFunction deploymentFunction) {
            this.deploymentFunction=deploymentFunction;
            return this;
        }

        public Builder<V> undeploymentFunction(VerticleUndeploymentFunction undeploymentFunction) {
            this.undeploymentFunction=undeploymentFunction;
            return this;
        }

        public VerticleDeploymentDescriptor<V> build() {
            if (deploymentFunction==null) {
                deploymentFunction=VerticleDeploymentFunction.DEFAULT;
            }
            if (undeploymentFunction==null) {
                undeploymentFunction=VerticleUndeploymentFunction.DEFAULT;
            }
            if (deployableFunction==null) {
                deployableFunction=(vertxSupplier,verticleSupplier,deploymentOptionsSupplier)->VerticleDeploymentDescriptor.createDeployAction(vertxSupplier,verticleSupplier,deploymentOptionsSupplier,deploymentFunction,undeploymentFunction);
            }
            return new VerticleDeploymentDescriptor<>(vertxSupplier,verticleSupplier,deploymentOptionsSupplier,deployableFunction);
        }
    }

    private static <V extends Verticle> Deployable<VerticleDeployment<V>,RuntimeException> createDeployAction(Supplier<Vertx> vertxSupplier,
                                                                                                              Supplier<V> verticleSupplier,
                                                                                                              Supplier<DeploymentOptions> deploymentOptionsSupplier,
                                                                                                              VerticleDeploymentFunction deploymentFunction,
                                                                                                              VerticleUndeploymentFunction undeploymentFunction) {
        return ()->{
            Vertx vertx=vertxSupplier.get();
            V verticle=verticleSupplier.get();
            DeploymentOptions deploymentOptions=deploymentOptionsSupplier.get();
            Future<String> future=deploymentFunction.deploy(vertx,verticle,deploymentOptions);
            return Futures.toCompletableFuture(future,deploymentId->
                VerticleDeployment.<V>builder().vertx(vertx).verticle(verticle).deploymentOptions(deploymentOptions).deploymentId(deploymentId).undeploymentFunction(undeploymentFunction).build()
            );
        };
    }
}
