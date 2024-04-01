package com.yelstream.topp.execution.concurrent.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Invoke manager addressing an executor services.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class ExecutorServiceInvokeManager implements InvokeManager {
    /**
     * Executor service used.
     */
    private final ExecutorService executorService;

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                         long timeout,
                                         TimeUnit unit) throws InterruptedException {
        return executorService.invokeAll(tasks,timeout,unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return executorService.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                           long timeout,
                           TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return executorService.invokeAny(tasks,timeout,unit);
    }
}
