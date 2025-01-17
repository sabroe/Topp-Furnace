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

package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Slide;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Space;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor(access= AccessLevel.PACKAGE)
public final class BufferCursorSet {
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

    private interface Operation {
        Buffer apply(Buffer buffer, int index, IntConsumer indexConsumer);
    }

    private BufferCursorSet op(Operation operation) {
        Buffer buffer=this.buffer;
        int index=slide.getIndex();
        AtomicInteger count=new AtomicInteger();
        IntConsumer countConsumer=c->count.set(c);
        buffer=operation.apply(buffer,index,countConsumer);  //TO-DO: Consider if the buffer reference changed or not!
        slide.setIndex(index+count.get());
        return this;
    }

    public BufferCursorSet setByte(byte b) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Byte.BYTES);
            return buffer.setByte(pos,b);
        });
    }

    public BufferCursorSet setUnsignedByte(short b) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            return buffer.setUnsignedByte(pos,b);
        });
    }

    public BufferCursorSet setInt(int i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            return buffer.setInt(pos,i);
        });
    }

    public BufferCursorSet setIntLE(int i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            return buffer.setIntLE(pos,i);
        });
    }

    public BufferCursorSet setUnsignedInt(long i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            return buffer.setUnsignedInt(pos,i);
        });
    }

    public BufferCursorSet setUnsignedIntLE(long i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            return buffer.setUnsignedIntLE(pos,i);
        });
    }

    public BufferCursorSet setMedium(int i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            return buffer.setMedium(pos,i);
        });
    }

    public BufferCursorSet setMediumLE(int i) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            return buffer.setMediumLE(pos,i);
        });
    }

    public BufferCursorSet setLong(long l) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            return buffer.setLong(pos,l);
        });
    }

    public BufferCursorSet setLongLE(long l) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            return buffer.setLongLE(pos,l);
        });
    }

    public BufferCursorSet setDouble(double d) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Double.BYTES);
            return buffer.setDouble(pos,d);
        });
    }

    public BufferCursorSet setFloat(float f) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Float.BYTES);
            return buffer.setFloat(pos,f);
        });
    }

    public BufferCursorSet setShort(short s) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            return buffer.setShort(pos,s);
        });
    }

    public BufferCursorSet setShortLE(short s) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            return buffer.setShortLE(pos,s);
        });
    }

    public BufferCursorSet setUnsignedShort(int s) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            return buffer.setUnsignedShort(pos,s);
        });
    }

    public BufferCursorSet setUnsignedShortLE(int s) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            return buffer.setUnsignedShortLE(pos,s);
        });
    }

    public BufferCursorSet setBuffer(Buffer b) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(b.length());
            return buffer.setBuffer(pos,b);
        });
    }

    public BufferCursorSet setBuffer(Buffer b,
                                     int offset,
                                     int len) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(len);
            return buffer.setBuffer(pos,b,offset,len);
        });
    }

    public BufferCursorSet setBytes(ByteBuffer b) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(b.limit());
            return buffer.setBytes(pos,b);
        });
    }

    public BufferCursorSet setBytes(byte[] b) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(b.length);
            return buffer.setBytes(pos,b);
        });
    }

    public BufferCursorSet setBytes(byte[] b,
                                    int offset,
                                    int len) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(len);
            return buffer.setBytes(pos,b,offset,len);
        });
    }

    public BufferCursorSet setString(String str) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(str.getBytes(StandardCharsets.UTF_8).length);
            return buffer.setString(pos,str);
        });
    }

    public BufferCursorSet setString(String str,
                                     Charset charset) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(str.getBytes(charset).length);
            return buffer.setString(pos,str,charset.name());
        });
    }
}
