/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.execution.thread.operation;

import com.yelstream.topp.execution.thread.InterruptedHandler;

/**
 * Sleep operation to be applied upon the current thread.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 */
@FunctionalInterface
public interface SleepOperation {
    /**
     * Causes the currently executing thread to sleep for the specified duration dictated by the operation.
     * @throws InterruptedException Thrown in case any thread has interrupted the current thread.
     *                              The interrupted status of the current thread is cleared when this exception is thrown.
     */
    void sleep() throws InterruptedException;

    /**
     * Creates a modified operation by catching {@link InterruptedException} instances and reacting by swallowing these instances.
     * @return New operation.
     */
    default SleepOperation onExSwallow() {
        return () -> {
            try {
                this.sleep();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        };
    }

    /**
     * Creates a modified operation by catching {@link InterruptedException} instances and reacting by handling these instances.
     * @param interruptedHandler Handler.
     * @return New operation.
     */
    default SleepOperation onExInspect(InterruptedHandler interruptedHandler) {
        return () -> {
            try {
                this.sleep();
            } catch (InterruptedException ex) {
                boolean handled=interruptedHandler!=null && interruptedHandler.handle(Thread.currentThread(),ex);
                if (handled) {
                    Thread.currentThread().interrupt();
                } else {
                    throw ex;  //Yes, do re-throw the existing exception!
                }
            }
        };
    }
}
