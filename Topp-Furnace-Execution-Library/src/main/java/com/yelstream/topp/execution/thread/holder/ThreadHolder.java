package com.yelstream.topp.execution.thread.holder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Accessor for a thread.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@RequiredArgsConstructor(staticName="of",access=AccessLevel.PACKAGE)
public class ThreadHolder {
    /**
     * Contained thread.
     */
    private final Thread thread;

    /**
     * Gets the contained thread.
     * @return Thread.
     */
    public Thread thread() {
        return thread;
    }
}
