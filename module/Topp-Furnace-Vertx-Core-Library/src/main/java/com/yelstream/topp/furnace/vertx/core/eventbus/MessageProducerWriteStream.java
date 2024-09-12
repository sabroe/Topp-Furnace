package com.yelstream.topp.furnace.vertx.core.eventbus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.streams.WriteStream;

public class MessageProducerWriteStream<T> implements WriteStream<T> {
    private final MessageProducer<T> producer;
    private Handler<Void> drainHandler;
    private boolean writable = true;

    public MessageProducerWriteStream(MessageProducer<T> producer) {
        this.producer = producer;
    }

    @Override
    public WriteStream<T> exceptionHandler(Handler<Throwable> handler) {
        // Implement if exception handling is needed
        return this;
    }

    @Override
    public Future<Void> write(T data) {
        Promise<Void> promise = Promise.promise();

        // Send data using MessageProducer and trigger backpressure handling
        producer.write(data, result -> {
            if (result.succeeded()) {
                writable = false;
                simulateWriteCompletion(promise);
            } else {
                promise.fail(result.cause());
            }
        });

        return promise.future();
    }

    @Override
    public void write(T data, Handler<AsyncResult<Void>> handler) {
        write(data).onComplete(handler);  // Call the handler when the write completes
    }

    @Override
    public Future<Void> end() {
        // Ending the stream, might perform cleanup or additional operations
        Promise<Void> promise = Promise.promise();
        producer.close(result -> {
            if (result.succeeded()) {
                promise.complete();
            } else {
                promise.fail(result.cause());
            }
        });
        return promise.future();
    }

    @Override
    public void end(Handler<AsyncResult<Void>> handler) {
        end().onComplete(handler);  // Call the handler when the stream ends
    }

    @Override
    public Future<Void> end(T data) {
        // Write the final data, then end the stream
        return write(data).compose(v -> end());
    }

    @Override
    public WriteStream<T> setWriteQueueMaxSize(int maxSize) {
        // No-op in this implementation, but could be extended
        return this;
    }

    @Override
    public boolean writeQueueFull() {
        // Return true when the stream is not writable
        return !writable;
    }

    @Override
    public WriteStream<T> drainHandler(Handler<Void> handler) {
        this.drainHandler = handler;
        return this;
    }

    private void simulateWriteCompletion(Promise<Void> promise) {
        // Simulate the completion of the write operation (async behavior)
        new Thread(() -> {
            try {
                Thread.sleep(500);  // Simulating backpressure delay
                writable = true;
                promise.complete();

                if (drainHandler != null) {
                    drainHandler.handle(null);  // Trigger drainHandler when writable again
                }
            } catch (InterruptedException e) {
                promise.fail(e);
            }
        }).start();
    }
}
