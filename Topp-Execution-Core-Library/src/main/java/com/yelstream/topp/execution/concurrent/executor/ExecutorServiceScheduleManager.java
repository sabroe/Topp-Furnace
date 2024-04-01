package com.yelstream.topp.execution.concurrent.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Schedule manager addressing an executor services.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class ExecutorServiceScheduleManager implements ScheduleManager {
    /**
     * Executor service used.
     */
    private final ScheduledExecutorService executorService;

    @Override
    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay,
                                       TimeUnit unit) {
        return executorService.schedule(command,delay,unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                           long delay,
                                           TimeUnit unit) {
        return executorService.schedule(callable,delay,unit);
    }


    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit) {
        return executorService.scheduleAtFixedRate(command,initialDelay,period,unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit) {
        return executorService.scheduleWithFixedDelay(command,initialDelay,delay,unit);
    }
}
