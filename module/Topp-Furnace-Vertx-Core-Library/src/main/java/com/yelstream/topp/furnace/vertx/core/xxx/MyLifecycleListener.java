package com.yelstream.topp.furnace.vertx.core.xxx;

import com.yelstream.topp.furnace.vertx.core.LifecycleListener;

public class MyLifecycleListener implements LifecycleListener {

    @Override
    public void onStartCalled() {
        System.out.println("Start called");
    }

    @Override
    public void onStartCompleted() {
        System.out.println("Start completed");
    }

    @Override
    public void onStartFailed(Throwable cause) {
        System.out.println("Start failed: " + cause.getMessage());
    }

    @Override
    public void onStopCalled() {
        System.out.println("Stop called");
    }

    @Override
    public void onStopCompleted() {
        System.out.println("Stop completed");
    }

    @Override
    public void onStopFailed(Throwable cause) {
        System.out.println("Stop failed: " + cause.getMessage());
    }
}
