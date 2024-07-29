package com.yelstream.topp.execution.manage;

/**
 *
 *
 * @param <E>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-29
 */
public interface Manageable<E extends Exception> extends AutoCloseable {

    LifecycleManager<E> getLifecycleManager();

    @Override
    default void close() {
        try {
            getLifecycleManager().close();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close managed component!",ex);
        }
    }
}
