package com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class DefaultSpace implements Space {
    /**
     *
     */
    private final IntSupplier lengthSupplier;

    /**
     *
     */
    private final Supplier<Gettable> gettableSupplier;

    /**
     *
     */
    private final Supplier<Puttable> puttableSupplier;

    public int getLength() {
        return lengthSupplier.getAsInt();
    }

    @Getter(lazy=true)
    private final Gettable gettable=this.gettableSupplier.get();

    @Getter(lazy=true)
    private final Puttable puttable=this.puttableSupplier.get();
}
