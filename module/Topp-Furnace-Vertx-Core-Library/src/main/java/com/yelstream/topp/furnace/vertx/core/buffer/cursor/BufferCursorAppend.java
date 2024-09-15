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

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Slide;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Space;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.UnaryOperator;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public final class BufferCursorAppend {
    /**
     *
     */
    private final BufferCursorWrite cursorWrite;

    /**
     *
     */
    private final Buffer buffer;

    /**
     * Buffer access.
     */
    private final Space space;

    /**
     * Settings for indexing into buffer.
     */
    private final Slide slide;

    public BufferCursorWrite end() {
        return cursorWrite;
    }

    private BufferCursorAppend op(UnaryOperator<Buffer> operation) {
        Buffer buffer=this.buffer;
        buffer=operation.apply(buffer);  //TO-DO: Consider if the buffer reference changed or not!
        return this;
    }

    public BufferCursorAppend appendBuffer(Buffer buff) {
        return op(buffer->buffer.appendBuffer(buff));
    }

    public BufferCursorAppend appendBuffer(Buffer buff,
                                          int offset,
                                          int len) {
        return op(buffer->buffer.appendBuffer(buff,offset,len));
    }

    public BufferCursorAppend appendBytes(byte[] bytes) {
        return op(buffer->buffer.appendBytes(bytes));
    }

    public BufferCursorAppend appendBytes(byte[] bytes,
                                         int offset,
                                         int len) {
        return op(buffer->buffer.appendBytes(bytes,offset,len));
    }

    public BufferCursorAppend appendByte(byte b) {
        return op(buffer->buffer.appendByte(b));
    }

    public BufferCursorAppend appendUnsignedByte(short b) {
        return op(buffer->buffer.appendUnsignedByte(b));
    }

    public BufferCursorAppend appendInt(int i) {
        return op(buffer->buffer.appendInt(i));
    }

    public BufferCursorAppend appendIntLE(int i) {
        return op(buffer->buffer.appendIntLE(i));
    }

    public BufferCursorAppend appendUnsignedInt(long i) {
        return op(buffer->buffer.appendUnsignedInt(i));
    }

    public BufferCursorAppend appendUnsignedIntLE(long i) {
        return op(buffer->buffer.appendUnsignedIntLE(i));
    }

    public BufferCursorAppend appendMedium(int i) {
        return op(buffer->buffer.appendMedium(i));
    }

    public BufferCursorAppend appendMediumLE(int i) {
        return op(buffer->buffer.appendMediumLE(i));
    }

    public BufferCursorAppend appendLong(long l) {
        return op(buffer->buffer.appendLong(l));
    }

    public BufferCursorAppend appendLongLE(long l) {
        return op(buffer->buffer.appendLongLE(l));
    }

    public BufferCursorAppend appendShortLE(short s) {
        return op(buffer->buffer.appendShortLE(s));
    }

    public BufferCursorAppend appendUnsignedShort(int s) {
        return op(buffer->buffer.appendUnsignedShort(s));
    }

    public BufferCursorAppend appendUnsignedShortLE(int s) {
        return op(buffer->buffer.appendUnsignedShortLE(s));
    }

    public BufferCursorAppend appendFloat(float f) {
        return op(buffer->buffer.appendFloat(f));
    }

    public BufferCursorAppend appendDouble(double d) {
        return op(buffer->buffer.appendDouble(d));
    }

    public BufferCursorAppend appendString(String str,
                                          String enc) {
        return op(buffer->buffer.appendString(str,enc));
    }

    public BufferCursorAppend appendString(String str) {
        return op(buffer->buffer.appendString(str));
    }
}
