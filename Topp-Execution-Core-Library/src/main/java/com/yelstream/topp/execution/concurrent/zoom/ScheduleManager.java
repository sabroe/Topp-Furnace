package com.yelstream.topp.execution.concurrent.zoom;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface ScheduleManager {

    ScheduledFuture<?> schedule(Runnable command,
                                long delay,
                                TimeUnit unit);

    <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                    long delay,
                                    TimeUnit unit);


    ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                           long initialDelay,
                                           long period,
                                           TimeUnit unit);

    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                              long initialDelay,
                                              long delay,
                                              TimeUnit unit);
}
