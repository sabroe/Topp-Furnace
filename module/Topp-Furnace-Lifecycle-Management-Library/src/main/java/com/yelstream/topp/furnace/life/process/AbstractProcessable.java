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

package com.yelstream.topp.furnace.life.process;

import com.yelstream.topp.furnace.life.process.op.Destroyable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract implementation of a processable component.
 * @param <S> Type of runnable.
 * @param <T> Type of result.
 * @param <E> Type of exception.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-29
 */
@RequiredArgsConstructor
public abstract class AbstractProcessable<S extends Destroyable<T,E>,T,E extends Exception,M extends ProcessManager<S,T,E>> implements Processable<S,T,E,M> {
    /**
     * Process manager.
     */
    @Getter
    private final M manager;
}
