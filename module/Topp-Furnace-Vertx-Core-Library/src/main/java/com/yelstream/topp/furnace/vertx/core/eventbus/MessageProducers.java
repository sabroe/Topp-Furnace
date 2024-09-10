package com.yelstream.topp.furnace.vertx.core.eventbus;

import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageProducer;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

/**
 * Utilities for instances of {@link MessageProducer}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@UtilityClass
public class MessageProducers {

    public static <T> Flow.Subscriber<Message<T>> createSubscriber(MessageProducer<T> producer) {
        return null;  //TO-DO: Fix!
    }
}
