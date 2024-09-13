package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AllArgsConstructor;

import java.io.DataInput;
import java.io.IOException;

@AllArgsConstructor
public class RegularCursorRead<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorRead<C,R,W> {
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    private final Gettable gettable;

    @Override
    public R dataInput(ConsumerWithException<DataInput, IOException> consumer) {
        return null;
    }

    @Override
    public C end() {
        return cursor;
    }
}
