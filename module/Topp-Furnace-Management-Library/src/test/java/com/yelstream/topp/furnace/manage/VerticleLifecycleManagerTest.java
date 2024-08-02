package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.manage.vertx.VerticleLifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.Test;

class VerticleLifecycleManagerTest {

    @Test
    void main() {

        Vertx vertx=null;
        Verticle verticle=null;

        try (VerticleLifecycleManager verticleManager = new VerticleLifecycleManager(vertx, verticle)) {
            /*Verticle manager = */verticleManager.start().join();
            // Do something with the verticle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
