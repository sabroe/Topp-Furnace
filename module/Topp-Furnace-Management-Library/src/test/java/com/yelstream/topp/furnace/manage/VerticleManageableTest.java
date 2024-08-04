package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.manage.vertx.VerticleLifecycleManager;
import com.yelstream.topp.furnace.manage.vertx.VerticleManageable;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class VerticleManageableTest {

    @Test
    void main() {

        Vertx vertx=null;
        Verticle verticle=null;

        try (VerticleManageable manageable=VerticleManageable.of(vertx,verticle)) {
            LifecycleManager<Verticle,String,Exception> manager=manageable.getManager();
            CompletableFuture<Verticle> startFuture=manager.start();
            CompletableFuture<String> stopFuture=manager.stop();
            // Do something with the verticle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
