package com.yelstream.topp.execution.concurrent.zoom;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ShutdownManager {

    void shutdown();

    List<Runnable> shutdownNow();

    boolean isShutdown();

    boolean isTerminated();

    boolean awaitTermination(long timeout,
                             TimeUnit unit)
            throws InterruptedException;



}
