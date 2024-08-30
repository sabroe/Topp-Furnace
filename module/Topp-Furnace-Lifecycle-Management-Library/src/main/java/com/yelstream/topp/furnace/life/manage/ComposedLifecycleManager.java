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

package com.yelstream.topp.furnace.life.manage;

import com.yelstream.topp.furnace.life.manage.op.Startable;
import com.yelstream.topp.furnace.life.manage.op.Stoppable;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract implementation of a lifecycle manager.
 * @param <S> Type of runnable.
 * @param <T> Type of result.
 * @param <E> Type of exception.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-27
 */
@RequiredArgsConstructor(staticName="of")
public class ComposedLifecycleManager<S,T,E extends Exception> implements LifecycleManager<S,T,E> {
    /**
     *
     */
    private final Startable<S,E> startable;

    /**
     *
     */
    private final Stoppable<T,E> stoppable;

    @Override
    public CompletableFuture<S> start() throws E {
        return startable.start();
    }

    @Override
    public CompletableFuture<T> stop() throws E {
        return stoppable.stop();
    }
}