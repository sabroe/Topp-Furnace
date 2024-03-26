package com.yelstream.topp.execution.concurrent;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Shutdown manager.
 * <p>
 *     The signature is inspired by {@link ExecutorService}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-27
 */
@SuppressWarnings({"unused","UnusedReturnValue"})
public interface ShutdownManager extends AutoCloseable {
    /**
     * Initiates an orderly shutdown.
     * <p>
     *   See {@link ExecutorService#shutdown()}.
     * </p>
     */
    void shutdown();

    /**
     * Attempts to stop all actively executing tasks.
     * <p>
     *   See {@link ExecutorService#shutdownNow()}.
     * </p>
     * @return Tasks that never commenced execution.
     */
    List<Runnable> shutdownNow();

    /**
     * Indicates, if this has been shut down.
     * <p>
     *   See {@link ExecutorService#isShutdown()}.
     * </p>
     * @return Indicates, if shutdown is successful.
     */
    boolean isShutdown();

    /**
     * Indicates, if this has been terminated.
     * <p>
     *   See {@link ExecutorService#isTerminated()}.
     * </p>
     * @return Indicates, if all tasks have completed following shut down.
     */
    boolean isTerminated();

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs,
     * or the current thread is interrupted, whichever happens first.
     * <p>
     *   See {@link ExecutorService#awaitTermination(long,TimeUnit)}.
     * </p>
     * @param timeout Wait timeout value.
     * @param unit Unit of wait timeout.
     * @return Indicates, if termination occurred before wait timeout.
     * @throws InterruptedException Thrown in case of interruption.
     */
    boolean awaitTermination(long timeout,
                             TimeUnit unit)
            throws InterruptedException;

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs,
     * or the current thread is interrupted, whichever happens first.
     * <p>
     *   See {@link #awaitTermination(long,TimeUnit)}.
     * </p>
     * @param timeout Wait timeout.
     * @return Indicates, if termination occurred before wait timeout.
     * @throws InterruptedException Thrown in case of interruption.
     */
    default boolean awaitTermination(Duration timeout) throws InterruptedException {
        return awaitTermination(timeout.toMillis(),TimeUnit.MILLISECONDS);
    }

    /**
     * Initiates a shutdown and awaits termination.
     * @param timeout Maximum duration to wait for termination.
     * @param unit Unit of timeout duration.
     * @return Indicates, if this has been terminated.
     */
    default boolean terminate(long timeout,
                              TimeUnit unit) {
        shutdown();
        boolean terminationCompleted=false;
        try {
            terminationCompleted=awaitTermination(timeout,unit);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return terminationCompleted;
    }

    /**
     * Initiates a shutdown and awaits termination.
     * @param timeout Maximum duration to wait for termination.
     * @return Indicates, if this has been terminated.
     */
    default boolean terminate(Duration timeout) {
        return terminate(timeout.toMillis(),TimeUnit.MILLISECONDS);
    }

    /**
     * Closes this.
     */
    void close();
}
