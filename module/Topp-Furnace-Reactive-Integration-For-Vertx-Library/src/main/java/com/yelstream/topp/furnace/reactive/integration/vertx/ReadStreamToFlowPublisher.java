package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.streams.ReadStream;

import java.util.concurrent.SubmissionPublisher;

public class ReadStreamToFlowPublisher<T> extends SubmissionPublisher<T> {

    public ReadStreamToFlowPublisher(ReadStream<T> readStream) {
        readStream.handler(this::submit)
                .exceptionHandler(this::closeExceptionally)
                .endHandler(v -> this.close());
    }
}
