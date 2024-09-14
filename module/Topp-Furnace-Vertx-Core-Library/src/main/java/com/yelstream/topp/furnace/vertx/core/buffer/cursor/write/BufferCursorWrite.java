package com.yelstream.topp.furnace.vertx.core.buffer.cursor.write;

import com.yelstream.topp.furnace.vertx.core.buffer.cursor.BufferCursor;
import com.yelstream.topp.furnace.vertx.core.buffer.cursor.read.BufferCursorRead;
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