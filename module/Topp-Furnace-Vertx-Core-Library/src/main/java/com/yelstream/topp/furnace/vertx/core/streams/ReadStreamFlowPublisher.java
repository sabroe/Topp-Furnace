/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.furnace.vertx.core.streams;

import com.yelstream.topp.execution.concurrent.flow.SubmissionPublishers;
import io.vertx.core.streams.ReadStream;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Publisher with a read stream as the source.
 * @param <T> Type of element.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
public class ReadStreamFlowPublisher<T> implements Flow.Publisher<T>, AutoCloseable {

    private final ReadStream<T> readStream;

    private final SubmissionPublisher<T> submissionPublisher;

    private final AtomicLong demand=new AtomicLong(0);

    public ReadStreamFlowPublisher(ReadStream<T> readStream,
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

    public ReadStreamFlowPublisher(ReadStream<T> readStream) {
        this(readStream,new SubmissionPublisher<>());
    }

    public ReadStreamFlowPublisher(ReadStream<T> readStream,
                                   Executor executor,
                                   int maxBufferCapacity) {
        this(readStream,SubmissionPublishers.<T>submissionPublisherBuilder().executor(executor).maxBufferCapacity(maxBufferCapacity).build());
    }

    public ReadStreamFlowPublisher(ReadStream<T> readStream,
                                   Executor executor) {
        this(readStream,SubmissionPublishers.<T>submissionPublisherBuilder().executor(executor).build());
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
                //Empty!
            }
        });
    }

    @Override
    public void close() {
        submissionPublisher.close();
    }
}
