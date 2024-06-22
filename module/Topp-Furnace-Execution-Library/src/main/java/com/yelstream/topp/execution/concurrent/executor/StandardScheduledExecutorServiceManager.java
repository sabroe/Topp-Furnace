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

import java.util.concurrent.ScheduledExecutorService;

/**
 * Standard scheduled executor manager.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@RequiredArgsConstructor(staticName="of")
public class StandardScheduledExecutorServiceManager implements ScheduledExecutorServiceManager {
    /**
     * Executor.
     */
    private final ScheduledExecutorService executorService;

    /**
     * Close operation.
     * This may be {@link null}.
     */
    private final Runnable close;

    @Override
    public InvokeManager getInvokeManager() {
        return ExecutorServiceInvokeManager.of(executorService);
    }

    @Override
    public SubmitManager getSubmitManager() {
        return ExecutorServiceSubmitManager.of(executorService);
    }

    @Override
    public ScheduleManager getScheduleManager() {
        return ExecutorServiceScheduleManager.of(executorService);
    }

    @Override
    public ShutdownManager getShutdownManager() {
        return ExecutorServiceShutdownManager.of(executorService);
    }

    @Override
    public void close() throws Exception {
        Runnables.run(close);
    }

    public static ScheduledExecutorServiceManager of(ScheduledExecutorService executorService) {
        return of(executorService,null);
    }
}
