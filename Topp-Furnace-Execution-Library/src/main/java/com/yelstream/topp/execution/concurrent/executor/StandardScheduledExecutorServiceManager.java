package com.yelstream.topp.execution.concurrent.executor;

import com.yelstream.topp.standard.lang.Runnables;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Standard scheduled executor manager.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@RequiredArgsConstructor(staticName="of")
public class StandardScheduledExecutorServiceManager implements ScheduledExecutorServiceManager {
    /**
     * Executor.
     */
    private final ScheduledExecutorService executorService;

    /**
     * Close operation.
     * This may be {@link null}.
     */
    private final Runnable close;

    @Override
    public InvokeManager getInvokeManager() {
        return ExecutorServiceInvokeManager.of(executorService);
    }

    @Override
    public SubmitManager getSubmitManager() {
        return ExecutorServiceSubmitManager.of(executorService);
    }

    @Override
    public ScheduleManager getScheduleManager() {
        return ExecutorServiceScheduleManager.of(executorService);
    }

    @Override
    public ShutdownManager getShutdownManager() {
        return ExecutorServiceShutdownManager.of(executorService);
    }

    @Override
    public void close() throws Exception {
        Runnables.run(close);
    }

    public static ScheduledExecutorServiceManager of(ScheduledExecutorService executorService) {
        return of(executorService,null);
    }
}
