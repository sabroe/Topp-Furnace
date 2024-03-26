package com.yelstream.topp.execution.concurrent;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;

/**
 * Utility addressing instances of {@link ShutdownManager}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@UtilityClass
public class ShutdownManagers {
    /**
     * Creates a shutdown manager around an existing executor.
     * @param executorService Executor service.
     * @return Created shutdown manager.
     */
    public static ShutdownManager createShutDownManager(ExecutorService executorService) {
        return ExecutorServiceShutdownManager.of(executorService);
    }
}
