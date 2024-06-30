package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Multi;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link Multi}.
 * <p>
 *     This contains conversions to and from {@link Flow.Publisher} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
@UtilityClass
public class Multis {
    /**
     * Create a multi from a publisher.
     * @param publisher Publisher.
     * @return Created multi.
     * @param <T> Type of item.
     */
    public static <T> Multi<T> fromPublisher(Flow.Publisher<T> publisher) {
        return Multi.createFrom().publisher(publisher);
    }

    /**
     * Create a publisher from a multi.
     * @param multi Multi.
     * @return Created publisher.
     * @param <T> Type of item.
     */
    public static <T> Flow.Publisher<T> toPublisher(Multi<T> multi) {
        return multi;
    }
}
