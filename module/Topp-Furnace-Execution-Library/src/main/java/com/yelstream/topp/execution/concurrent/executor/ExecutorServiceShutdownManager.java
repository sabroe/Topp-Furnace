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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Shutdown manager addressing an executor service.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-27
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
@SuppressWarnings("java:S1117")
class ExecutorServiceShutdownManager implements ShutdownManager {
    /**
     * Executor service used.
     */
    @Getter
    private final ExecutorService executorService;

    private final AtomicBoolean alive=new AtomicBoolean(true);

    @Override
    public void shutdown() {
        log.trace("Shutdown manager #shutdown().");
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        log.trace("Shutdown manager #shutdownNow().");
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        log.trace("Shutdown manager #isShutdown().");
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        log.trace("Shutdown manager #isTerminated().");
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout,
                                    TimeUnit unit) throws InterruptedException {
        log.trace("Shutdown manager #awaitTermination(long,TimeUnit).");
        return executorService.awaitTermination(timeout,unit);
    }

    @Override
    public boolean awaitTermination(Duration timeout) throws InterruptedException {
        log.trace("Shutdown manager #awaitTermination(Duration).");
        return ShutdownManager.super.awaitTermination(timeout);
    }

    @Override
    public boolean terminate(long timeout,
                             TimeUnit unit) {
        log.trace("Shutdown manager #terminate(long,TimeUnit).");
        return ShutdownManager.super.terminate(timeout,unit);
    }

    @Override
    public boolean terminate(Duration timeout) {
        log.trace("Shutdown manager #terminate(Duration).");
        return ShutdownManager.super.terminate(timeout);
    }

    @Override
    public void close() {
        log.trace("Shutdown manager #close().");
        if (alive.compareAndSet(true,false)) {
            log.debug("Shutdown manager closing.");
            shutdownNow();
        }
    }
}
