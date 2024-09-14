package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.function.Supplier;

@Getter
@Setter
@AllArgsConstructor
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class CursorState {
    /**
     * Default character-set.
     */
    public static final Charset DEFAULT_CHARSET=StandardCharsets.UTF_8;

    /**
     *
     */
    private final Supplier<Gettable> gettableSupplier;

    /**
     *
     */
    private final Supplier<Puttable> puttableSupplier;

    /**
     * Current index into the Vert.x buffer.
     */
    @lombok.Builder.Default
    private int index=0;

    /**
     * Character-set to use, if doing textual parsing.
     */
    @lombok.Builder.Default
    private Charset charset=DEFAULT_CHARSET;

    /**
     *
     */
    @lombok.Builder.Default
    private ByteOrder byteOrder=null;

    /**
     *
     */
    @lombok.Builder.Default
    private Locale locale=null;
}
