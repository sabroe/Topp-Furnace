package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Schedule manager.
 * <p>
 *     The signature is inspired by {@link ExecutorService}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
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
