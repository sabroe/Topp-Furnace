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

package com.yelstream.topp.furnace.vertx.core.buffer.io;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import io.vertx.core.buffer.Buffer;
import lombok.AllArgsConstructor;

/**
 * Exposes the content of a byte buffer for writing.
 * <p>
 *   This is a lower performant interface.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-11
 */
@AllArgsConstructor
public class BufferPuttable implements Puttable {
    /**
     * Vert.x buffer.
     */
    private final Buffer buffer;  //TO-DO: Consider bufferReference=new AtomicReference<Buffer>(buffer)! For expansion, possibly slicing!

    @Override
    public int length() {
        return buffer.length();
    }

    @Override
    public void put(int index, byte b) {
        buffer.setByte(index,b);
    }

    @Override
    public void put(int index, byte[] src, int offset, int length) {  //TO-DO: Consider making this auto-expandable!
        buffer.setBytes(index,src,offset,length);
    }
}
