package com.yelstream.topp.execution.concurrent;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Utility addressing instances of {@link ThreadFactory}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@Slf4j
@UtilityClass
public class ThreadFactories {
    /**
     * Creates a default thread factory.
     * @return Thread factory.
     */
    public static ThreadFactory createThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    /**
     * Creates a default thread factory.
     * @param builder Thread builder.
     * @return Thread factory.
     */
    public static ThreadFactory createThreadFactory(Thread.Builder builder) {
        return builder.factory();
    }
}
