package com.yelstream.topp.execution.concurrent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Shutdown manager addressing an executor.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-27
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
@SuppressWarnings("java:S1117")
class ExecutorServiceShutdownManager implements ShutdownManager {
    /**
     * Executor service used.
     */
    @Getter
    private final ExecutorService executorService;

    private final AtomicBoolean alive=new AtomicBoolean(true);

    @Override
    public void shutdown() {
        log.trace("Shutdown manager #shutdown().");
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        log.trace("Shutdown manager #shutdownNow().");
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        log.trace("Shutdown manager #isShutdown().");
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        log.trace("Shutdown manager #isTerminated().");
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout,
                                    TimeUnit unit) throws InterruptedException {
        log.trace("Shutdown manager #awaitTermination(long,TimeUnit).");
        return executorService.awaitTermination(timeout,unit);
    }

    @Override
    public boolean awaitTermination(Duration timeout) throws InterruptedException {
        log.trace("Shutdown manager #awaitTermination(Duration).");
        return ShutdownManager.super.awaitTermination(timeout);
    }

    @Override
    public boolean terminate(long timeout,
                             TimeUnit unit) {
        log.trace("Shutdown manager #terminate(long,TimeUnit).");
        return ShutdownManager.super.terminate(timeout,unit);
    }

    @Override
    public boolean terminate(Duration timeout) {
        log.trace("Shutdown manager #terminate(Duration).");
        return ShutdownManager.super.terminate(timeout);
    }

    @Override
    public void close() {
        log.trace("Shutdown manager #close().");
        if (alive.compareAndSet(true,false)) {
            log.debug("Shutdown manager closing.");
            shutdownNow();
        }
    }
}
