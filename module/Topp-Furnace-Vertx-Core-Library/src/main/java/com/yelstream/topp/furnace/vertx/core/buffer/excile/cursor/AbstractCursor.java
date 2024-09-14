package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
public abstract class AbstractCursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements Cursor<C,R,W> {
    /**
     *
     */
    protected CursorState state;

    protected AbstractCursor(CursorState state) {
        this.state=state;
    }

    protected AbstractCursor(Supplier<Gettable> gettableSupplier,
                             Supplier<Puttable> puttableSupplier) {
        this.state=CursorState.builder().gettableSupplier(gettableSupplier).puttableSupplier(puttableSupplier).build();
    }
}
