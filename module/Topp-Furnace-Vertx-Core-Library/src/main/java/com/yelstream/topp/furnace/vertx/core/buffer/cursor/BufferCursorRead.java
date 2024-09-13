package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.RegularCursorRead;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;

public class BufferCursorRead extends RegularCursorRead<BufferCursor,BufferCursorRead,BufferCursorWrite> {
    public BufferCursorRead(BufferCursor cursor, Gettable gettable) {
        super(cursor, gettable);
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
