package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.ExecutorService;

/**
 * Capable of being shut down.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
public interface Shutdownable {
    ShutdownManager getShutdownManager();
}
