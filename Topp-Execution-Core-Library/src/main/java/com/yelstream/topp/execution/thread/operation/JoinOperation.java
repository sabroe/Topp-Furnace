package com.yelstream.topp.execution.thread.operation;

import com.yelstream.topp.execution.thread.InterruptedHandler;

/**
 * Join operation to be applied upon a thread.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@FunctionalInterface
public interface JoinOperation {
    /**
     * Waits for a thread to terminate.
     * @param thread Thread to wait for.
     * @return Indicates, if the wait is a success and the thread is terminated.
     * @throws InterruptedException Thrown in case any thread has interrupted the current thread.
     *                              The interrupted status of the current thread is cleared when this exception is thrown.
     */
    boolean join(Thread thread) throws InterruptedException;

    /**
     * Creates a modified operation by catching {@link InterruptedException} instances and reacting by swallowing these instances.
     * @return New operation.
     */
    default JoinOperation onExSwallow() {
        return thread -> {
            try {
                return this.join(thread);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return false;
            }
        };
    }

    /**
     * Creates a modified operation by catching {@link InterruptedException} instances and reacting by handling these instances.
     * @param interruptedHandler Handler.
     * @return New operation.
     */
    default JoinOperation onExInspect(InterruptedHandler interruptedHandler) {
        return thread -> {
            try {
                return this.join(thread);
            } catch (InterruptedException ex) {
                boolean handled=interruptedHandler!=null && interruptedHandler.handle(thread,ex);
                if (handled) {
                    Thread.currentThread().interrupt();
                    return false;
                } else {
                    throw ex;  //Yes, do re-throw the existing exception!
                }
            }
        };
    }
}
