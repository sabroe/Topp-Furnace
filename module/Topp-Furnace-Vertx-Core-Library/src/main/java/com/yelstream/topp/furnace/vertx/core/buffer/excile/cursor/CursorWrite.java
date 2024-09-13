package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;

import java.io.DataOutput;
import java.io.IOException;

public interface CursorWrite<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> {

    W dataOutput(ConsumerWithException<DataOutput, IOException> consumer);

    C end();
}
