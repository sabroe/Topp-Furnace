package com.yelstream.topp.furnace.vertx.core.eventbus;

import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.streams.ReadStream;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

@UtilityClass
public class MessageConsumers {

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static <T> Flow.Publisher<Message<T>> createPublisherForMessage(MessageConsumer<T> consumer) {
        ReadStream<Message<T>> readStream=consumer;
        return toPublisher(readStream);
    }

    public static <T> Flow.Publisher<T> createPublisherForMessageBody(MessageConsumer<T> consumer) {
        ReadStream<T> readStream=consumer.bodyStream();
        return toPublisher(readStream);
    }

    /**
     * Create a publisher from a read-stream.
     * @param readStream Reast-stream.
     * @return Created publisher.
     * @param <T> Type of item.
     */
    public static <T> Flow.Publisher<T> toPublisher(ReadStream<T> readStream) {
return null;  //        return FlowAdapters.toFlowPublisher(FlowableHelper.toFlowable(readStream));
    }

}
