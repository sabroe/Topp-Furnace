package com.yelstream.topp.furnace.reactive.integration.reactor.core.publisher;

import org.reactivestreams.FlowAdapters;
import reactor.core.publisher.Flux;

import java.util.concurrent.Flow;

public class Fluxes {




    public static <T> Flux<T> fromPublisher(Flow.Publisher<T> publisher) {
        return Flux.from(FlowAdapters.toPublisher(publisher));
    }

    public static <T> Flow.Publisher<T> toPublisher(Flux<T> flux) {
        return FlowAdapters.toFlowPublisher(flux);
    }
}
