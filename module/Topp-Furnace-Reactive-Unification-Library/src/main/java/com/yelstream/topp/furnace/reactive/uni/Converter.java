package com.yelstream.topp.furnace.reactive.uni;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.experimental.UtilityClass;
import org.reactivestreams.FlowAdapters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@UtilityClass
public class Converter {

    public static <T> Uni<T> fromMonoToUni(Mono<T> mono) {
        return Uni.createFrom().publisher(FlowAdapters.toFlowPublisher(mono));
    }

    public static <T> Mono<T> fromUniToMono(Uni<T> uni) {
        return Mono.from(FlowAdapters.toPublisher(uni.convert().toPublisher()));
    }

    public static <T> Multi<T> fromFluxToMulti(Flux<T> flux) {
        return Multi.createFrom().publisher(FlowAdapters.toFlowPublisher(flux));
    }

    public static <T> Flux<T> fromMultiToFlux(Multi<T> multi) {
        return Flux.from(FlowAdapters.toPublisher(multi.convert().toPublisher()));
    }
}
