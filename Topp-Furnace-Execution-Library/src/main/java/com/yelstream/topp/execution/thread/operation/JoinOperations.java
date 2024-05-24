package com.yelstream.topp.execution.thread.operation;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * Utilities addressing instance of {@link JoinOperation}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@Slf4j
@UtilityClass
public class JoinOperations {
    /**
     * Creates a join operator with reference to {@link Thread#join()}.
     * @return Created join operator.
     */
    public static JoinOperation create() {
        return thread -> {
            thread.join();
            return thread.getState()==Thread.State.TERMINATED;
        };
    }

    /**
     * Creates a join operator with reference to {@link Thread#join(long)}.
     * @param millis Time to wait in milliseconds.
     * @return Created join operator.
     */
    public static JoinOperation create(long millis) {
        return thread -> {
            thread.join(millis);
            return thread.getState()==Thread.State.TERMINATED;
        };
    }

    /**
     * Creates a join operator with reference to {@link Thread#join(long,int)}.
     * @param millis Time to wait in milliseconds.
     * @param nanos Time to wait in nanoseconds.
     * @return Created join operator.
     */
    public static JoinOperation create(long millis, int nanos) {
        return thread -> {
            thread.join(millis,nanos);
            return thread.getState()==Thread.State.TERMINATED;
        };
    }

    /**
     * Creates a join operator with reference to {@link Thread#join(Duration)}.
     * @param duration Time to wait.
     * @return Created join operator.
     */
    public static JoinOperation create(Duration duration) {
        return thread->thread.join(duration);
    }
}
