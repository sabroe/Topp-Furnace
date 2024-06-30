package com.yelstream.topp.furnace.reactive.integration.reactor.core.publisher;

import lombok.experimental.UtilityClass;
import org.reactivestreams.FlowAdapters;
import reactor.core.publisher.Flux;

import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link Flux}.
 * <p>
 *     This contains conversions to and from {@link Flow.Publisher} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
@UtilityClass
public class Fluxes {
    /**
     * Create a flux from a publisher.
     * @param publisher Publisher.
     * @return Created flux.
     * @param <T> Type of item.
     */
    public static <T> Flux<T> fromPublisher(Flow.Publisher<T> publisher) {
        return Flux.from(FlowAdapters.toPublisher(publisher));
    }

    /**
     * Create a publisher from a flux.
     * @param flux Flux.
     * @return Created publisher.
     * @param <T> Type of item.
     */
    public static <T> Flow.Publisher<T> toPublisher(Flux<T> flux) {
        return FlowAdapters.toFlowPublisher(flux);
    }
}
