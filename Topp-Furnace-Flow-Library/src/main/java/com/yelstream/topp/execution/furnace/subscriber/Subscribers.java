package com.yelstream.topp.execution.furnace.subscriber;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

/**
 * Utility addressing instances of {@link Flow.Subscriber}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-29
 */@UtilityClass
public class Subscribers {


    public static <T> Flow.Subscriber<T> verify(Flow.Subscriber<T> subscriber) {
return null;
    }

    public static <T> Flow.Subscriber<T> protect(Flow.Subscriber<T> subscriber) {
        return null;
    }
}
