package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Submit manager.
 * <p>
 *     The signature is inspired by {@link ExecutorService}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
public interface SubmitManager {



    Future<?> submit(Runnable task);

    <T> Future<T> submit(Runnable task,
                         T result);

    <T> Future<T> submit(Callable<T> task);

}
