package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.RegularCursorRead;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;

public class BufferCursorRead extends RegularCursorRead<BufferCursor,BufferCursorRead,BufferCursorWrite> {
    public BufferCursorRead(BufferCursor cursor, Gettable gettable) {
        super(cursor, gettable);
    }
}
