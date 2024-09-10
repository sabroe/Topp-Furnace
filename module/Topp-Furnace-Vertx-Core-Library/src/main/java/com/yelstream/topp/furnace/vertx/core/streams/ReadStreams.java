/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import io.vertx.core.streams.ReadStream;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executor;
import java.util.concurrent.SubmissionPublisher;

/**
 * Utility addressing instances of {@link ReadStream}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@UtilityClass
public class ReadStreams {

    public static <T> ReadStreamFlowPublisher<T> createPublisher(ReadStream<T> readStream) {
        return new ReadStreamFlowPublisher<>(readStream);
    }

    public static <T>ReadStreamFlowPublisher<T> createPublisher(ReadStream<T> readStream,
                                                                Executor executor,
                                                                int maxBufferCapacity) {
        return new ReadStreamFlowPublisher<>(readStream,executor,maxBufferCapacity);
    }

    public static <T>ReadStreamFlowPublisher<T> createPublisher(ReadStream<T> readStream,
                                                                SubmissionPublisher<T> submissionPublisher) {
        return new ReadStreamFlowPublisher<>(readStream,submissionPublisher);
    }
}