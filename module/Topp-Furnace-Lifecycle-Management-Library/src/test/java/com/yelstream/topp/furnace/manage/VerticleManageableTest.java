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
