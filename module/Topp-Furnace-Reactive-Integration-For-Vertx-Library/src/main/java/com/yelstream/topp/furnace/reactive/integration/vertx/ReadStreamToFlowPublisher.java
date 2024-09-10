package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.streams.ReadStream;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicLong;

public class ReadStreamToFlowPublisher<T> implements Flow.Publisher<T> {

    private final ReadStream<T> readStream;

    private final SubmissionPublisher<T> submissionPublisher;

    private final AtomicLong demand=new AtomicLong(0);

    public ReadStreamToFlowPublisher(ReadStream<T> readStream,
                                     SubmissionPublisher<T> submissionPublisher) {
        this.readStream=readStream;
        this.submissionPublisher=submissionPublisher;

        readStream.handler(item -> {
            submissionPublisher.submit(item);
            if (demand.decrementAndGet()==0) {
                readStream.pause();
            }
        }).exceptionHandler(submissionPublisher::closeExceptionally)
        .endHandler(v -> submissionPublisher.close());
    }

    public ReadStreamToFlowPublisher(ReadStream<T> readStream) {
        this(readStream,new SubmissionPublisher<>());
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        submissionPublisher.subscribe(subscriber);

        subscriber.onSubscribe(new Flow.Subscription() {
            @Override
            public void request(long n) {
                if (n<=0) {
                    submissionPublisher.closeExceptionally(new IllegalArgumentException("Non-positive subscription request"));
                } else {
                    demand.addAndGet(n);
                    readStream.resume();
                }
            }

            @Override
            public void cancel() {
                submissionPublisher.close();
            }
        });
    }
}
