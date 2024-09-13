package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AllArgsConstructor;

import java.io.DataOutput;
import java.io.IOException;

@AllArgsConstructor
public class RegularCursorWrite<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorWrite<C,R,W> {
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    private final Puttable puttable;

    @Override
    public W dataOutput(ConsumerWithException<DataOutput, IOException> consumer) {
        return null;
    }

    @Override
    public C end() {
        return cursor;
    }
}
