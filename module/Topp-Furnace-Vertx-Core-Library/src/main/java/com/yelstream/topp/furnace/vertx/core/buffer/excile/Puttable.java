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

package com.yelstream.topp.furnace.vertx.core.buffer.excile;

/**
 * Exposes the content of a byte buffer for writing.
 * <p>
 *   This is a lower performant interface.
 *   However, the signatures of the absolute,
 *   indexed {@code put} methods are inspired by the method signatures found within {@link java.nio.ByteBuffer}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-11
 */
public interface Puttable {

    int length();

    default void put(byte[] src) {
        put(0,src);
    }

    void put(int index, byte b);

    default void put(int index, byte[] src) {
        put(index,src,0,src.length);
    }

    void put(int index, byte[] src, int offset, int length);
}
