package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.Executor;

/**
 * Executor manager.
 * <p>
 *     The signature is inspired by {@link Executor}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
public interface ExecutorManager extends AutoCloseable {
    /**
     * Executes a command.
     * @param command Command.
     */
    void execute(Runnable command);
}
