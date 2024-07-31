package com.yelstream.topp.furnace.manage;

/**
 *
 *
 * @param <E>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-29
 */
public interface LifecycleManager<S,T,E extends Exception> extends Startable<S,E>, Stoppable<T,E>, AutoCloseable {

    @Override
    default void close() throws E {
        try {
            stop().join();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close lifecycle manager!",ex);
        }
    }
}
