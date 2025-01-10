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
