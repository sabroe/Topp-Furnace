package com.yelstream.topp.execution.manage;

import java.util.concurrent.CompletableFuture;

/**
 *
 *
 * @param <E>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-29
 */
public interface LifecycleManager<E extends Exception> extends AutoCloseable {
    CompletableFuture<Void> start() throws E;
    CompletableFuture<Void> stop() throws E;

    @Override
    default void close() {
        try {
            stop().join();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close lifecycle manager!",ex);
        }
    }
}
