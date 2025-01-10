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

package com.yelstream.topp.furnace.life.process;

import com.yelstream.topp.furnace.life.process.op.Destroyable;

/**
 * Component creating managed processes.
 * @param <S> Type of runnable.
 * @param <T> Type of result.
 * @param <E> Type of exception.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-04
 */
public interface Processable<S extends Destroyable<T,E>,T,E extends Exception,M extends ProcessManager<S,T,E>> extends AutoCloseable {
    /**
     * Gets the manager creating components.
     * @return Manager creating components.
     */
    M getManager();

    @Override
    default void close() throws E {
        try {
            getManager().close();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close managed component!",ex);
        }
    }
}
