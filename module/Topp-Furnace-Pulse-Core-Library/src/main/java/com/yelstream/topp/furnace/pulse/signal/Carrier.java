package com.yelstream.topp.furnace.pulse.signal;

import com.yelstream.topp.furnace.pulse.signal.codec.Codec;

import java.time.Instant;

/**
 *
 *
 *
 * @param <S> Type of signal.
 */
public interface Carrier<S> {

    Instant produceTimestamp();

    Instant consumeTimestamp();

    String correlationId();

    Codec codeec();  //Or codec name!

    Address source();

    Address target();

    S signal();

}
