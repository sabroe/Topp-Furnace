package com.yelstream.topp.furnace.reactive.uni;

import reactor.core.publisher.Mono;
import io.smallrye.mutiny.Uni;

public class Example {
    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Hello from Reactor Mono");
        Uni<String> uni = Converter.fromMonoToUni(mono);

        uni.subscribe().with(item -> System.out.println("Received: " + item));

        Uni<String> anotherUni = Uni.createFrom().item("Hello from Mutiny Uni");
        Mono<String> anotherMono = Converter.fromUniToMono(anotherUni);

        anotherMono.subscribe(item -> System.out.println("Received: " + item));
    }
}
