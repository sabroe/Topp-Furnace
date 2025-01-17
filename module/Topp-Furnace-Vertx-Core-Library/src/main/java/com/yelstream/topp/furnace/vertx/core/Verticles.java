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

import com.yelstream.topp.furnace.vertx.core.function.VerticleDeploymentFunction;
import com.yelstream.topp.furnace.vertx.core.function.VerticleUndeploymentFunction;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Verticle}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@UtilityClass
public class Verticles {

    public <V extends Verticle> VerticleDeploymentDescriptor<V> deploymentDescriptor(Vertx vertx,
                                                                                     V verticle,
                                                                                     DeploymentOptions deploymentOptions) {
        VerticleDeploymentDescriptor.Builder<V> builder=VerticleDeploymentDescriptor.builder();
        builder.vertx(vertx).verticle(verticle).deploymentOptions(deploymentOptions);
        return builder.build();
    }

    public <V extends Verticle> VerticleDeploymentDescriptor<V> deploymentDescriptor(Vertx vertx,
                                                                                     Supplier<V> verticleSupplier,
                                                                                     DeploymentOptions deploymentOptions,
                                                                                     VerticleDeploymentFunction deploymentFunction,
                                                                                     VerticleUndeploymentFunction undeploymentFunction) {
        VerticleDeploymentDescriptor.Builder<V> builder=VerticleDeploymentDescriptor.builder();
        builder.vertx(vertx).verticleSupplier(verticleSupplier).deploymentOptions(deploymentOptions);
        builder.deploymentFunction(deploymentFunction);
        builder.undeploymentFunction(undeploymentFunction);
        return builder.build();
    }
}
