package com.yelstream.topp.furnace.vertx.core.buffer.cursor.write;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorState;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

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
     *
     */
    private final CursorState state;

    public BufferCursorWrite end() {
        return cursorWrite;
    }

    private interface Operation {
        Buffer apply(Buffer buffer, int index, IntConsumer indexConsumer);
    }

    private BufferCursorSet op(Operation operation) {
        Buffer buffer=this.buffer;
        int index=state.getIndex();
        AtomicInteger count=new AtomicInteger();
        IntConsumer countConsumer=c->count.set(c);
        buffer=operation.apply(buffer,index,countConsumer);  //TO-DO: Consider if the buffer reference changed or not!
        state.setIndex(index+count.get());
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
