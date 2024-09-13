package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

public interface Cursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> {

    R read();

    W write();

    C end();
}
