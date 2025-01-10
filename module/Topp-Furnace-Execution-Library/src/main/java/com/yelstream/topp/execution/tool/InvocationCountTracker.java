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

package com.yelstream.topp.execution.tool;

import com.yelstream.topp.execution.concurrent.atomic.AtomicIntegers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracker of invocation count.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-05
 */
@NoArgsConstructor(staticName="of")
@ToString
public class InvocationCountTracker {
    /**
     * Accumulated number of invocations initiated.
     */
    private final AtomicLong accumulated=new AtomicLong();

    /**
     * Currently active invocations running.
     */
    private final AtomicInteger active=new AtomicInteger();

    /**
     * Maximum, active invocations having run at the same time.
     */
    private final AtomicInteger maximumActive=new AtomicInteger();

    /**
     * Provider of read access to invocation count statistics.
     */
    public final class Reader {
        /**
         * Gets the accumulated number of invocations initiated.
         * @return Accumulated number of invocations initiated.
         */
        public long accumulated() {
            return accumulated.get();
        }

        /**
         * Gets the currently active invocations running.
         * @return Currently active invocations running.
         */
        public int active() {
            return active.get();
        }

        /**
         * Gets the maximum, active invocations having run at the same time.
         * @return Maximum, active invocations having run at the same time.
         */
        public int maximumActive() {
            return maximumActive.get();
        }
    }

    /**
     * Gets the provider of read access to invocation count statistics.
     */
    @Getter
    private final Reader reader=new Reader();

    /**
     * Registers the beginning of an invocation.
     * @return Currently active invocations.
     */
    protected int invocationBegin() {
        accumulated.incrementAndGet();
        @SuppressWarnings("java:S1117")
        int active=this.active.incrementAndGet();
        AtomicIntegers.updateMax(maximumActive,active);
        return active;
    }

    /**
     * Registers the ending of an invocation.
     */
    protected void invocationEnd() {
        active.decrementAndGet();
    }

    /**
     * Tracker of a single invocation.
     */
    @Getter
    public final class Invocation implements AutoCloseable {
        private final int active;

        /**
         * Constructor.
         * Registers the beginning of an invocation.
         */
        private Invocation() {
            active=invocationBegin();
        }

        /**
         * Registers the ending of an invocation.
         */
        @Override
        public void close() {
            invocationEnd();
        }
    }

    /**
     * Creates a new invocation tracker.
     * @return Invocation tracker.
     */
    public Invocation count() {
        return new Invocation();
    }
}
