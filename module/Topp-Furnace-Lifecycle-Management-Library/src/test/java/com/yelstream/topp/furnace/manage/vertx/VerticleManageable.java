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

import com.yelstream.topp.furnace.manage.AbstractManageable;
import com.yelstream.topp.furnace.manage.LifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Manageable implementation for a Vert.x Verticle.
 *
 * @version 1.0
 * @since 2024-07-29
 */
public class VerticleManageable extends AbstractManageable<Verticle,String,Exception,LifecycleManager<Verticle,String,Exception>> {

    @Getter
    private final Vertx vertx;

    @Getter
    private final Verticle verticle;

    @lombok.Builder(builderClassName="Builder",access=AccessLevel.PRIVATE)
    private VerticleManageable(Vertx vertx,
                               Verticle verticle,
                               LifecycleManager<Verticle,String,Exception> manager) {
        super(manager);
        this.vertx=vertx;
        this.verticle=verticle;
    }

    public static VerticleManageable of(Verticle verticle) {
        return builder().verticle(verticle).build();
    }

    public static VerticleManageable of(Vertx vertx,
                                        Verticle verticle) {
        return builder().vertx(vertx).verticle(verticle).build();
    }

    private static class Builder {
        public VerticleManageable build() {
            if (vertx==null) {
                vertx=verticle.getVertx();
            }
            if (manager==null) {
                manager=VerticleLifecycleManager.of(vertx,verticle);
            }
            return new VerticleManageable(vertx,verticle,manager);
        }
    }
}
