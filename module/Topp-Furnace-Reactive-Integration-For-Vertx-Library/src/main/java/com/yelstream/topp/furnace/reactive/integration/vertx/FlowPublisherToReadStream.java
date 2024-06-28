package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FlowPublisherToReadStream<T> implements ReadStream<T> {

    private final Flow.Publisher<T> publisher;
    private final AtomicReference<Handler<T>> dataHandler = new AtomicReference<>();
    private final AtomicReference<Handler<Void>> endHandler = new AtomicReference<>();
    private final AtomicReference<Handler<Throwable>> exceptionHandler = new AtomicReference<>();
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private Subscription subscription;

    public FlowPublisherToReadStream(Flow.Publisher<T> publisher) {
        this.publisher = publisher;
        this.publisher.subscribe(new Flow.Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                FlowPublisherToReadStream.this.subscription = subscription;
                subscription.request(1); // Request the first item
            }

            @Override
            public void onNext(T item) {
                Handler<T> handler = dataHandler.get();
                if (handler != null) {
                    handler.handle(item);
                }
                if (!isPaused.get()) {
                    subscription.request(1); // Request the next item
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Handler<Throwable> handler = exceptionHandler.get();
                if (handler != null) {
                    handler.handle(throwable);
                }
            }

            @Override
            public void onComplete() {
                Handler<Void> handler = endHandler.get();
                if (handler != null) {
                    handler.handle(null);
                }
            }
        });
    }

    @Override
    public ReadStream<T> exceptionHandler(Handler<Throwable> handler) {
        exceptionHandler.set(handler);
        return this;
    }

    @Override
    public ReadStream<T> handler(Handler<T> handler) {
        dataHandler.set(handler);
        return this;
    }

    @Override
    public ReadStream<T> pause() {
        isPaused.set(true);
        return this;
    }

    @Override
    public ReadStream<T> resume() {
        if (isPaused.compareAndSet(true, false)) {
            subscription.request(1); // Request the next item after resuming
        }
        return this;
    }

    @Override
    public ReadStream<T> fetch(long amount) {
        subscription.request(amount);
        return this;
    }

    @Override
    public ReadStream<T> endHandler(Handler<Void> handler) {
        endHandler.set(handler);
        return this;
    }
}
