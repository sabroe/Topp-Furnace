package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.RegularCursorWrite;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;

public class BufferCursorWrite extends RegularCursorWrite<BufferCursor, BufferCursorRead, BufferCursorWrite> {
    public BufferCursorWrite(BufferCursor cursor, Puttable puttable) {
        super(cursor, puttable);
    }
}