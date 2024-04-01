package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.ExecutorService;

/**
 * Scheduled executor service manager.
 * <p>
 *     The signature is inspired by {@link ScheduledExecutorService}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
public interface ScheduledExecutorServiceManager extends ExecutorServiceManager {

    ScheduleManager getScheduleManager();
}
