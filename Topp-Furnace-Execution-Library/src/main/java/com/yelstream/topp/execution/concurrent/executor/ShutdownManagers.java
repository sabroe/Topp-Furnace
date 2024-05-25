/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.execution.concurrent.executor;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;

/**
 * Utility addressing instances of {@link ShutdownManager}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@UtilityClass
public class ShutdownManagers {
    /**
     * Creates a shutdown manager around an existing executor.
     * @param executorService Executor service.
     * @return Created shutdown manager.
     */
    public static ShutdownManager createShutDownManager(ExecutorService executorService) {
        return ExecutorServiceShutdownManager.of(executorService);
    }
}
