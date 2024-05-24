package com.yelstream.topp.execution.thread.operation;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * Utilities addressing instances of {@link SleepOperation}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@Slf4j
@UtilityClass
public class SleepOperations {
    /**
     * Creates a sleep operator with reference to {@link Thread#yield()}.
     * @return Created join operator.
     */
    public static SleepOperation create() {
        return Thread::yield;
    }

    /**
     * Creates a sleep operator with reference to {@link Thread#sleep(long)}.
     * @param millis Time to wait in milliseconds.
     * @return Created join operator.
     */
    public static SleepOperation create(long millis) {
        return ()->Thread.sleep(millis);
    }

    /**
     * Creates a sleep operator with reference to {@link Thread#sleep(long,int)}.
     * @param millis Time to wait in milliseconds.
     * @param nanos Time to wait in nanoseconds.
     * @return Created join operator.
     */
    public static SleepOperation create(long millis, int nanos) {
        return ()->Thread.sleep(millis,nanos);
    }

    /**
     * Creates a sleep operator with reference to {@link Thread#sleep(Duration)}.
     * @param duration Time to wait.
     * @return Created join operator.
     */
    public static SleepOperation create(Duration duration) {
        return ()->Thread.sleep(duration);
    }
}
