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

package com.yelstream.topp.furnace.reactive.integration.reactivestreams;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Processor;

import java.util.concurrent.Flow;

/**
 * Associates different processors, a Flow processor and a Reactive Streams processor, one wrapping the other.
 * <p>
 *     The internal wrapping is done through {@link FlowAdapters}.
 * </p>
 * @param <T> Subscribed item type.
 * @param <R> Published item type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-27
 */
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
public class CrossProcessor<T,R> {
    /**
     * Flow processor.
     */
    private final Flow.Processor<T,R> flowProcessor;

    /**
     * Reactive Streams processor.
     */
    private final Processor<T,R> reactiveProcessor;

    /**
     * Actually used Flow processor, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Flow.Processor<T,R> lazyFlowProcessor=initFlowProcessor();

    /**
     * Actually used Reactive Streams processor, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Processor<T,R> lazyReactiveProcessor=initReactiveProcessor();

    /**
     * Initializes the used Flow processor.
     * @return Flow processor.
     */
    private Flow.Processor<T,R> initFlowProcessor() {
        if (flowProcessor!=null) {
            return flowProcessor;
        } else {
            if (reactiveProcessor==null) {
                return null;
            } else {
                return FlowAdapters.toFlowProcessor(reactiveProcessor);
            }
        }
    }

    /**
     * Initializes the used Reactive Streams processor.
     * @return Reactive Streams processor.
     */
    private Processor<T,R> initReactiveProcessor() {
        if (reactiveProcessor!=null) {
            return reactiveProcessor;
        } else {
            if (flowProcessor==null) {
                return null;
            } else {
                return FlowAdapters.toProcessor(flowProcessor);
            }
        }
    }

    /**
     * Gets the Flow processor.
     * @return Flow processor.
     */
    public Flow.Processor<T,R> getFlowProcessor() {
        return getLazyFlowProcessor();
    }

    /**
     * Gets the Reactive Streams processor.
     * @return Reactive Streams processor.
     */
    public Processor<T,R> getReactiveProcessor() {
        return getLazyReactiveProcessor();
    }

    /**
     * Creates a new cross processor.
     * @param flowProcessor Flow processor.
     * @return Created cross processor.
     * @param <T> Subscribed item type.
     * @param <R> Published item type.
     */
    public static <T,R> CrossProcessor<T,R> of(Flow.Processor<T,R> flowProcessor) {
        return of(flowProcessor,null);
    }

    /**
     * Creates a new cross processor.
     * @param reactiveProcessor Reactive Streams processor.
     * @return Created cross processor.
     * @param <T> Subscribed item type.
     * @param <R> Published item type.
     */
    public static <T,R> CrossProcessor<T,R> of(Processor<T,R> reactiveProcessor) {
        return of(null,reactiveProcessor);
    }
}
