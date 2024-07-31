package com.yelstream.topp.furnace.manage.impl;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.io.IOException;

public class Test {

    public static void main(String[] args) {

        Vertx vertx=null;
        Verticle verticle=null;

        try (ProcessSpawnManager processManager = new ProcessSpawnManager(new ProcessBuilder("myCommand"))) {
            ProcessManager process = processManager.start().join();
            // Do something with the process
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (VerticleSpawnManager verticleManager = new VerticleSpawnManager(vertx, verticle)) {
            VerticleManager manager = verticleManager.start().join();
            // Do something with the verticle
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ProcessLifecycleManager processManager = new ProcessLifecycleManager(new ProcessBuilder("myCommand"))) {
            Process process = processManager.start().join();
            // Do something with the process
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (VerticleLifecycleManager verticleManager = new VerticleLifecycleManager(vertx, verticle)) {
            Verticle manager = verticleManager.start().join();
            // Do something with the verticle
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
