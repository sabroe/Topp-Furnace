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

package com.yelstream.topp.execution.concurrent.flow;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.BiConsumer;

/**
 * Utility addressing instances of {@link SubmissionPublisher}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2023-12-14
 */
@UtilityClass
public class SubmissionPublishers {
    /**
     * Gets the default executor used by {@link SubmissionPublisher} instances.
     * @return Executor.
     */
    public static Executor getDefaultExecutor() {
        try (SubmissionPublisher<Object> submissionPublisher=new SubmissionPublisher<>();) {
            /*
             * Note:
             *   Access to the private and static 'SubmissionPublisher#ASYNC_POOL' is gained through a dummy instance!
             *   This is essentially implementation specific, but it compensates for the dumb implementation of 'SubmissionPublisher'.
             */
            return submissionPublisher.getExecutor();
        }
    }

    /**
     * Gets the default maximum buffer capacity used by {@link SubmissionPublisher} instances.
     * @return Maximum buffer capacity.
     */
    public static int getDefaultMaxBufferCapacity() {
        return Flow.defaultBufferSize();
    }

    /**
     * Creates a new {@link SubmissionPublisher}.
     * @param executor Executor.
     * @param maxBufferCapacity Maximum buffer capacity.
     * @param handler Handler.
     * @return Publisher.
     * @param <T> Item type.
     */
    @lombok.Builder(builderClassName="SubmissionPublisherBuilder",builderMethodName="submissionPublisherBuilder")
    public static <T> SubmissionPublisher<T> createSubmissionPublisher(Executor executor,
                                                                       int maxBufferCapacity,
                                                                       BiConsumer<? super Subscriber<? super T>, ? super Throwable> handler) {
        return new SubmissionPublisher<>(executor!=null?executor:getDefaultExecutor(),
            maxBufferCapacity>0?maxBufferCapacity:getDefaultMaxBufferCapacity(),
            handler);
    }

    /**
     * Creates a new {@link SubmissionPublisher}.
     * @return Publisher.
     * @param <T> Item type.
     */
    public static <T> SubmissionPublisher<T> createSubmissionPublisher() {
        return new SubmissionPublisher<>();
    }
}
