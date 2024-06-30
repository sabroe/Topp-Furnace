package com.yelstream.topp.furnace.reactive.integration.vertx.core.streams;

import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.streams.ReadStream;
import io.vertx.rxjava3.FlowableHelper;
import lombok.experimental.UtilityClass;
import org.reactivestreams.FlowAdapters;

import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link ReadStream}.
 * <p>
 *     This contains conversions to and from {@link Flow.Publisher} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-29
 */
@UtilityClass
public class ReadStreams {
    /**
     * Create a read-stream from a publisher.
     * @param publisher Publisher.
     * @return Created read-stream.
     * @param <T> Type of item.
     */
    public static <T> ReadStream<T> fromPublisher(Flow.Publisher<T> publisher) {
        Flowable<T> flowable=Flowable.fromPublisher(FlowAdapters.toPublisher(publisher));
        return FlowableHelper.toReadStream(flowable);
    }

    /**
     * Create a publisher from a read-stream.
     * @param readStream Reast-stream.
     * @return Created publisher.
     * @param <T> Type of item.
     */
    public static <T> Flow.Publisher<T> toPublisher(ReadStream<T> readStream) {
        return FlowAdapters.toFlowPublisher(FlowableHelper.toFlowable(readStream));
    }
}
