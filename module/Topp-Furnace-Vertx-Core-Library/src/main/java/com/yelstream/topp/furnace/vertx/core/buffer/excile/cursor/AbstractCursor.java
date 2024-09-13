package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import lombok.AllArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
public abstract class AbstractCursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements Cursor<C,R,W> {
    /**
     *
     */
    protected abstract C getCursor();

    /**
     *
     */
    private final Function<C,Gettable> gettableSupplier;

    /**
     *
     */
    private final Function<C,Puttable> puttableSupplier;

    /**
     *
     */
    private final BiFunction<C,Gettable,R> cursorReadSupplier;

    /**
     *
     */
    private final BiFunction<C,Puttable,W> cursorWriteSupplier;

    /**
     * Character set to use, if doing textual parsing.
     */
    private final Charset charset=StandardCharsets.UTF_8;

    /**
     * Current index into the Vert.x buffer.
     */
    private int index;


    @Override
    public R read() {
        C cursor=getCursor();
        Gettable gettable=gettableSupplier.apply(cursor);
        return cursorReadSupplier.apply(cursor,gettable);
    }

    @Override
    public W write() {
        C cursor=getCursor();
        Puttable puttable=puttableSupplier.apply(cursor);
        return cursorWriteSupplier.apply(cursor,puttable);
    }

    @Override
    public C end() {
        return getCursor();
    }
}
