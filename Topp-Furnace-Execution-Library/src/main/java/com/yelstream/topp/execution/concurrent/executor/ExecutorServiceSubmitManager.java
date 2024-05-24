package com.yelstream.topp.execution.concurrent.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Submit manager addressing an executor service.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class ExecutorServiceSubmitManager implements SubmitManager {
    /**
     * Executor service used.
     */
    private final ExecutorService executorService;

    @Override
    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task,
                                T result) {
        return executorService.submit(task,result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }
}
