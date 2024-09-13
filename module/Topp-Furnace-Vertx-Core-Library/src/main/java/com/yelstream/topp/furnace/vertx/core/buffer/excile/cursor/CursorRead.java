package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;

import java.io.DataInput;
import java.io.IOException;

public interface CursorRead<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> {

    R dataInput(ConsumerWithException<DataInput, IOException> consumer);

    C end();
}
