package com.yelstream.topp.furnace.reactive.uni;

import io.reactivex.rxjava3.core.Flowable;  //RxJava3
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;  //SmallRye Mutiny, used in Quarkus
import io.vertx.core.streams.ReadStream;  //Vert.x Streams
import io.vertx.core.streams.WriteStream;
import io.vertx.rxjava3.FlowableHelper;
//import io.vertx.rxjava3.WriteStreamSubscriber;
//import io.vertx.rxjava3.impl.WriteStreamSubscriberImpl;
import lombok.experimental.UtilityClass;
import org.reactivestreams.FlowAdapters;  //Reactive Streams
import reactor.core.publisher.Flux;  //Reactor Core, used in Spring WebFlex
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

    // Vert.x conversions
    public static <T> Multi<T> fromReadStreamToMulti(ReadStream<T> readStream) {
        return Multi.createFrom().publisher(FlowAdapters.toFlowPublisher(FlowableHelper.toFlowable(readStream)));
    }

    public static <T> Flux<T> fromReadStreamToFlux(ReadStream<T> readStream) {
/*
        Flowable<T> flowable=FlowableHelper.toFlowable(readStream);
        org.reactivestreams.Publisher<T> publisher=flowable;  <-- Yes, there IS a publisher here!
        return Flux.from(publisher);
*/
        return Flux.from(FlowableHelper.toFlowable(readStream));  //Note: 'Publisher' hidden in the form of the 'Flowable'!
    }

    public static <T> ReadStream<T> fromMultiToReadStream(Multi<T> multi) {
        Flowable<T> flowable = Flowable.fromPublisher(FlowAdapters.toPublisher(multi.convert().toPublisher()));
        return FlowableHelper.toReadStream(flowable);
    }

    public static <T> ReadStream<T> fromFluxToReadStream(Flux<T> flux) {
        Flowable<T> flowable = Flowable.fromPublisher(flux);
        return FlowableHelper.toReadStream(flowable);
    }

    /********/

    public static <T> Uni<Void> fromWriteStreamToUni(WriteStream<T> writeStream, T value) {  //Nah, this is tracking a single write-job!
        return Uni.createFrom().completionStage(writeStream.write(value).toCompletionStage());
    }

    public static <T> Mono<Void> fromWriteStreamToMono(WriteStream<T> writeStream, T value) {  //Nah, this is tracking a single write-job!
        return Mono.fromCompletionStage(writeStream.write(value).toCompletionStage());
    }

    /********/

    /*
        Note: Getting from a RXJava3 'WriteStream' to a 'Flow.Subscriber' can be done by using RxHelper:

            public static <T> WriteStreamSubscriber<T> toSubscriber(WriteStream<T> stream) {
                return toSubscriber(stream, Function.identity());
            }

            public static <R, T> WriteStreamSubscriber<R> toSubscriber(WriteStream<T> stream, Function<R, T> mapping) {
                return new WriteStreamSubscriberImpl<>(stream, mapping);
            }
     */
}
