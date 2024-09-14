package com.yelstream.topp.furnace.vertx.core.buffer.cursor.write;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorState;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.UnaryOperator;

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
     *
     */
    private final CursorState state;

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
