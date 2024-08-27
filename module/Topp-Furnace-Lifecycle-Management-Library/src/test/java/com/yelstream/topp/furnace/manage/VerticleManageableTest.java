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

package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.manage.vertx.VerticleManageable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class VerticleManageableTest {

    @Test
    void main() {

        Vertx vertx = Vertx.vertx();
        Verticle verticle = new TestVerticle();

        try (VerticleManageable manageable=VerticleManageable.of(vertx,verticle)) {
            LifecycleManager<Verticle,String,Exception> manager=manageable.getManager();
            CompletableFuture<Verticle> startFuture=manager.start();
            CompletableFuture<String> stopFuture=manager.stop();
            // Do something with the verticle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Slf4j
    static class TestVerticle extends AbstractVerticle {
        @Override
        public void start() {
            log.debug("Verticle started!");
        }

        @Override
        public void stop() {
            log.debug("Verticle stopped!");
        }
    }
}
