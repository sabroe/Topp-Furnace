package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public abstract class AbstractCursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements Cursor<C,R,W> {
    /**
     * Character set to use, if doing textual parsing.
     */
    protected final Charset charset=StandardCharsets.UTF_8;

    /**
     * Current index into the Vert.x buffer.
     */
    protected int index;
}
