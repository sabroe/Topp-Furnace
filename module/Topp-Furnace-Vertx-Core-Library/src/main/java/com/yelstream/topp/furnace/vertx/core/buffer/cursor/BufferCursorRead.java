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

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.AbstractCursorRead;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorState;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import io.vertx.core.buffer.Buffer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
public final class BufferCursorRead extends AbstractCursorRead<BufferCursor,BufferCursorRead,BufferCursorWrite> {
    /**
     * Vert.x buffer.
     */
    private final Buffer buffer;  //TO-DO: Consider bufferReference=new AtomicReference<Buffer>(buffer)! For expansion, possibly slicing!

    BufferCursorRead(BufferCursor cursor,
                     Buffer buffer,
                     Gettable gettable,
                     CursorState state) {
        super(cursor,gettable,state);
        this.buffer=buffer;
    }

    @Override
    protected BufferCursorRead getThis() {
        return this;
    }

    public byte getByte(int pos) {
        return 0;
    }

    public short getUnsignedByte(int pos) {
        return 0;
    }

    public int getInt(int pos) {
        return 0;
    }

    public int getIntLE(int pos) {
        return 0;
    }

    public long getUnsignedInt(int pos) {
        return 0;
    }

    public long getUnsignedIntLE(int pos) {
        return 0;
    }

    public long getLong(int pos) {
        return 0;
    }

    public long getLongLE(int pos) {
        return 0;
    }

    public double getDouble(int pos) {
        return 0;
    }

    public float getFloat(int pos) {
        return 0;
    }

    public short getShort(int pos) {
        return 0;
    }

    public short getShortLE(int pos) {
        return 0;
    }

    public int getUnsignedShort(int pos) {
        return 0;
    }

    public int getUnsignedShortLE(int pos) {
        return 0;
    }

    public int getMedium(int pos) {
        return 0;
    }

    public int getMediumLE(int pos) {
        return 0;
    }

    public int getUnsignedMedium(int pos) {
        return 0;
    }

    public int getUnsignedMediumLE(int pos) {
        return 0;
    }





    public String getString(int start,
                            int end,
                            String enc) {
        return null;
    }

    public String getString(int start,
                            int end) {
        return null;
    }






/*
    int length()

    Buffer copy()

    Buffer slice()

    Buffer slice(int start,
                 int end)
*/



}
