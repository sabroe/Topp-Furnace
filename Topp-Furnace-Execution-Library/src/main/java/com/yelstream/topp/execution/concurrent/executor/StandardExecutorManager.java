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

import com.yelstream.topp.standard.lang.Runnables;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executor;

/**
 * Standard executor manager.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@RequiredArgsConstructor(staticName="of")
public class StandardExecutorManager implements ExecutorManager {
    /**
     * Executor.
     */
    private final Executor executor;

    /**
     * Close operation.
     * This may be {@link null}.
     */
    private final Runnable close;

    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }

    @Override
    public void close() throws Exception {
        Runnables.run(close);
    }

    public static StandardExecutorManager of(Executor executor) {
        return of(executor,null);
    }
}
