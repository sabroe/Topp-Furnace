package com.yelstream.topp.furnace.reactive.integration.vertx.core.streams;

import io.vertx.core.streams.ReadStream;
import org.reactivestreams.FlowAdapters;

import java.util.concurrent.Flow;

public class ReadStreams {

/*
    public static <T> ReadStream<T> fromPublisher(Flow.Publisher<T> publisher) {
        Flowable<T> flowable = Flowable.fromPublisher(FlowAdapters.toPublisher(multi.convert().toPublisher()));
        return FlowableHelper.toReadStream(flowable);
    }

    public static <T> Flow.Publisher<T> toPublisher(ReadStream<T> readStream) {
        return FlowAdapters.toFlowPublisher(flux);
    }
*/

/*
    public static <T> ReadStream<T> fromMultiToReadStream(Multi<T> multi) {
        Flowable<T> flowable = Flowable.fromPublisher(FlowAdapters.toPublisher(multi.convert().toPublisher()));
        return FlowableHelper.toReadStream(flowable);
    }

    public static <T> ReadStream<T> fromFluxToReadStream(Flux<T> flux) {
        Flowable<T> flowable = Flowable.fromPublisher(flux);
        return FlowableHelper.toReadStream(flowable);
    }
*/
}
