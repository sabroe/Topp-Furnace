package com.yelstream.topp.execution.furnace.submitter;

/**
 * Submitter of items.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-29
 *
 * @param <T> Type of item.
 */
public interface Submitter<T> {
    /**
     * Submits an item.
     * @param item Item.
     */
    void submit(T item);
}
