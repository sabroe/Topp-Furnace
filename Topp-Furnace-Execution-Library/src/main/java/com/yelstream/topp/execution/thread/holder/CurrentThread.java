package com.yelstream.topp.execution.thread.holder;

import lombok.extern.slf4j.Slf4j;

/**
 * Accessor for the current thread.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@Slf4j
public class CurrentThread {
    /**
     * Creates a holder.
     * @return Holder.
     */
    public ThreadHolder holder() {
        return ThreadHolder.of(Thread.currentThread());
    }
}
