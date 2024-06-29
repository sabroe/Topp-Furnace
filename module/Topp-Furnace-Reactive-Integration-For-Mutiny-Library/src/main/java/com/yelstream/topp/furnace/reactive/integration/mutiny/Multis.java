package com.yelstream.topp.furnace.reactive.integration.mutiny;

import io.smallrye.mutiny.Multi;

import java.util.concurrent.Flow;

public class Multis {


    public static <T> Multi<T> fromPublisher(Flow.Publisher<T> publisher) {
        return Multi.createFrom().publisher(publisher);
    }

    public static <T> Flow.Publisher<T> toPublisher(Multi<T> multi) {
        return multi;
    }
}
