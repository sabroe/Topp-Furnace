package com.yelstream.topp.execution.thread;

import lombok.experimental.UtilityClass;

/**
 * Addresses instances of {@link Thread}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@UtilityClass
@SuppressWarnings({"unused","UnusedReturnValue"})
public class Threads {

    /**
     * Indicates, if a thread is in a specific state.
     * @param thread Thread.
     * @param state State.
     * @return Indicates, if thread is in state.
     */
    public static boolean isInState(Thread thread,
                                    Thread.State state) {
        return thread.getState()==state;
    }

    /**
     * Indicates, if a thread is terminated.
     * @param thread Thread.
     * @return Indicates, if thread is terminated.
     */
    public static boolean isTerminated(Thread thread) {
        return isInState(thread,Thread.State.TERMINATED);
    }
}
