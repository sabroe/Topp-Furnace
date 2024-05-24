package com.yelstream.topp.execution.thread;

/**
 * Handler of interrupted exceptions from thread invocations.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@FunctionalInterface
public interface InterruptedHandler {
    /**
     * Handles an exception from a thread invocation.
     * @param thread Thread.
     * @param ex Exception.
     * @return Indicates, if the exception is handled. If not then it should be re-thrown.
     */
    boolean handle(Thread thread,
                   InterruptedException ex);

    /**
     * Handler doing nothing.
     */
    InterruptedHandler UNHANDLED=(thread,ex)->false;

    /**
     * Default handler.
     */
    InterruptedHandler DEFAULT=UNHANDLED;
}
