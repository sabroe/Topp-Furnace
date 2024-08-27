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

package com.yelstream.topp.furnace.manage.op;

import java.util.concurrent.CompletableFuture;

/**
 * Capable of initiating the creation of a running component.
 * @param <S> Type of runnable.
 * @param <E> Type of exception.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-04
 */
@FunctionalInterface
public interface Creatable<S,E extends Exception> {
    /**
     * Initiates a create operation.
     * @return Handle to the result of the operation.
     * @throws E Thrown in case of error.
     */
    CompletableFuture<S> create() throws E;  //TO-DO: Further consider and evaluate the need and sanity of a checked exception -- in relation to actual implementations!
}
