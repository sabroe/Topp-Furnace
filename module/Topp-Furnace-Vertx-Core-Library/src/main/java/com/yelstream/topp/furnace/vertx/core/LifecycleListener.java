package com.yelstream.topp.furnace.vertx.core;

public interface LifecycleListener {

    void onStartCalled();

    void onStartCompleted();

    void onStartFailed(Throwable cause);

    void onStopCalled();

    void onStopCompleted();

    void onStopFailed(Throwable cause);


}
