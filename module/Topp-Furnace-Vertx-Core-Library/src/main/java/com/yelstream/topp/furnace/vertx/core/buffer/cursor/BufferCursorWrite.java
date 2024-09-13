package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.RegularCursorWrite;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import io.vertx.core.buffer.Buffer;

import java.nio.ByteBuffer;

public class BufferCursorWrite extends RegularCursorWrite<BufferCursor, BufferCursorRead, BufferCursorWrite> {
    public BufferCursorWrite(BufferCursor cursor, Puttable puttable) {
        super(cursor, puttable);
    }

    @Override
    protected BufferCursorWrite getThis() {
        return this;
    }

    public BufferCursorWrite appendBuffer(Buffer buff) {

        return this;
    }

    public BufferCursorWrite appendBuffer(Buffer buff,
                        int offset,
                        int len) {

        return this;
    }

    public BufferCursorWrite appendBytes(byte[] bytes) {

        return this;
    }

    public BufferCursorWrite appendBytes(byte[] bytes,
                       int offset,
                       int len) {

        return this;
    }

    public BufferCursorWrite appendByte(byte b) {

        return this;
    }

    public BufferCursorWrite appendUnsignedByte(short b) {

        return this;
    }

    public BufferCursorWrite appendInt(int i) {

        return this;
    }

    public BufferCursorWrite appendIntLE(int i) {

        return this;
    }

    public BufferCursorWrite appendUnsignedInt(long i) {

        return this;
    }

    public BufferCursorWrite appendUnsignedIntLE(long i) {

        return this;
    }

    public BufferCursorWrite appendMedium(int i) {

        return this;
    }

    public BufferCursorWrite appendMediumLE(int i) {

        return this;
    }

    public BufferCursorWrite appendLong(long l) {

        return this;
    }

    public BufferCursorWrite appendLongLE(long l) {

        return this;
    }

    public BufferCursorWrite appendShortLE(short s) {

        return this;
    }

    public BufferCursorWrite appendUnsignedShort(int s) {

        return this;
    }

    public BufferCursorWrite appendUnsignedShortLE(int s) {

        return this;
    }

    public BufferCursorWrite appendFloat(float f) {

        return this;
    }

    public BufferCursorWrite appendDouble(double d) {

        return this;
    }

    public BufferCursorWrite appendString(String str,
                        String enc) {

        return this;
    }

    public BufferCursorWrite appendString(String str) {

        return this;
    }

    public BufferCursorWrite setByte(int pos,
                   byte b) {

        return this;
    }

    public BufferCursorWrite setUnsignedByte(int pos,
                           short b) {

        return this;
    }

    public BufferCursorWrite setInt(int pos,
                  int i) {

        return this;
    }

    public BufferCursorWrite setIntLE(int pos,
                    int i) {

        return this;
    }

    public BufferCursorWrite setUnsignedInt(int pos,
                          long i) {

        return this;
    }

    public BufferCursorWrite setUnsignedIntLE(int pos,
                            long i) {

        return this;
    }

    public BufferCursorWrite setMedium(int pos,
                     int i) {

        return this;
    }

    public BufferCursorWrite setMediumLE(int pos,
                       int i) {

        return this;
    }

    public BufferCursorWrite setLong(int pos,
                   long l) {

        return this;
    }

    public BufferCursorWrite setLongLE(int pos,
                     long l) {

        return this;
    }

    public BufferCursorWrite setDouble(int pos,
                     double d) {

        return this;
    }

    public BufferCursorWrite setFloat(int pos,
                    float f) {

        return this;
    }

    public BufferCursorWrite setShort(int pos,
                    short s) {

        return this;
    }

    public BufferCursorWrite setShortLE(int pos,
                      short s) {

        return this;
    }

    public BufferCursorWrite setUnsignedShort(int pos,
                            int s) {

        return this;
    }

    public BufferCursorWrite setUnsignedShortLE(int pos,
                              int s) {

        return this;
    }

    public BufferCursorWrite setBuffer(int pos,
                     Buffer b) {

        return this;
    }

    public BufferCursorWrite setBuffer(int pos,
                     Buffer b,
                     int offset,
                     int len) {

        return this;
    }

    public BufferCursorWrite setBytes(int pos,
                    ByteBuffer b) {

        return this;
    }

    public BufferCursorWrite setBytes(int pos,
                    byte[] b) {

        return this;
    }

    public BufferCursorWrite setBytes(int pos,
                    byte[] b,
                    int offset,
                    int len) {

        return this;
    }

    public BufferCursorWrite setString(int pos,
                     String str) {

        return this;
    }

    public BufferCursorWrite setString(int pos,
                     String str,
                     String enc) {

        return this;
    }
}