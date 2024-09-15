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

package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.AbstractCursorWrite;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorState;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import io.vertx.core.buffer.Buffer;

public final class BufferCursorWrite extends AbstractCursorWrite<BufferCursor,BufferCursorRead,BufferCursorWrite> {
    /**
     * Vert.x buffer.
     */
    private final Buffer buffer;  //TO-DO: Consider bufferReference=new AtomicReference<Buffer>(buffer)! For expansion, possibly slicing!

    public BufferCursorWrite(BufferCursor cursor,
                             Buffer buffer,
                             Puttable puttable,
                             CursorState state) {
        super(cursor,puttable,state);
        this.buffer=buffer;
    }

    @Override
    protected BufferCursorWrite getThis() {
        return this;
    }

    public BufferCursorAppend append() {
        return new BufferCursorAppend(this,buffer,state);
    }

    public BufferCursorSet set() {
        return new BufferCursorSet(this,buffer,state);
    }
}