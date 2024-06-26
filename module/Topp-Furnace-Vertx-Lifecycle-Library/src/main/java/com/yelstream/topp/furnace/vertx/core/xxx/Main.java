package com.yelstream.topp.furnace.vertx.core.xxx;

import io.vertx.core.Vertx;

public class Main {

    public static void main(String[] args) {
        // Create a Vert.x instance
        Vertx vertx = Vertx.vertx();

        // Deploy MainVerticle
        vertx.deployVerticle(new MainVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("MainVerticle deployment id: " + res.result());
            } else {
                System.out.println("MainVerticle deployment failed: " + res.cause());
            }
        });

        // Add a shutdown hook to gracefully stop Vert.x
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook initiated. Stopping Vert.x gracefully...");
            // Close Vert.x instance gracefully
            vertx.close(res -> {
                if (res.succeeded()) {
                    System.out.println("Vert.x stopped gracefully.");
                } else {
                    System.out.println("Failed to stop Vert.x: " + res.cause());
                }
            });
        }));

        // Wait for 5 seconds, then stop the verticle
        vertx.setTimer(5000, id -> {
            System.out.println("Stopping MainVerticle...");
            vertx.deploymentIDs().forEach(deploymentId -> {
                vertx.undeploy(deploymentId, undeployRes -> {
                    if (undeployRes.succeeded()) {
                        System.out.println("MainVerticle undeployed successfully.");
                    } else {
                        System.out.println("Failed to undeploy MainVerticle: " + undeployRes.cause());
                    }
                });
            });
        });
    }

}
