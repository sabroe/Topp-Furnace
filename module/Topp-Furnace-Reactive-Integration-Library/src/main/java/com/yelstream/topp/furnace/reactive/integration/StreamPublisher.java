package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class StreamPublisher<T> implements Publisher<T> {

    private final java.util.stream.Stream<T> stream;

    public StreamPublisher(java.util.stream.Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        stream.forEach(subscriber::onNext);
        subscriber.onComplete();
    }
}
