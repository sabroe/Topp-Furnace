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

package com.yelstream.topp.furnace.reactive.integration.cross;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Subscriber;

import java.util.concurrent.Flow;

/**
 * Associates different subscribers, a Flow subscriber and a Reactive Streams subscriber, one wrapping the other.
 * <p>
 *     The internal wrapping is done through {@link FlowAdapters}.
 * </p>
 * @param <T> Item type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-27
 */
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
public class CrossSubscriber<T> {
/*
    public enum BackPressureControl {
        CONTROLS_BACK_PRESSURE,
        DOES_NOT_CONTROL_BACK_PRESSURE
    }
    private final BackPressureControl backPressureControl;  //TO-DO: Consider adding this!
*/

    /**
     * Flow subscriber.
     */
    private final Flow.Subscriber<T> flowSubscriber;

    /**
     * Reactive Streams subscriber.
     */
    private final Subscriber<T> reactiveSubscriber;

    /**
     * Actually used Flow subscriber, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Flow.Subscriber<T> lazyFlowSubscriber=initFlowSubscriber();

    /**
     * Actually used Reactive Streams subscriber, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Subscriber<T> lazyReactiveSubscriber=initReactiveSubscriber();

    /**
     * Initializes the used Flow subscriber.
     * @return Flow subscriber.
     */
    private Flow.Subscriber<T> initFlowSubscriber() {
        if (flowSubscriber!=null) {
            return flowSubscriber;
        } else {
            if (reactiveSubscriber==null) {
                return null;
            } else {
                return FlowAdapters.toFlowSubscriber(reactiveSubscriber);
            }
        }
    }

    /**
     * Initializes the used Reactive Streams subscriber.
     * @return Reactive Streams subscriber.
     */
    private Subscriber<T> initReactiveSubscriber() {
        if (reactiveSubscriber!=null) {
            return reactiveSubscriber;
        } else {
            if (flowSubscriber==null) {
                return null;
            } else {
                return FlowAdapters.toSubscriber(flowSubscriber);
            }
        }
    }

    /**
     * Gets the Flow subscriber.
     * @return Flow subscriber.
     */
    public Flow.Subscriber<T> getFlowSubscriber() {
        return getLazyFlowSubscriber();
    }

    /**
     * Gets the Reactive Streams subscriber.
     * @return Reactive Streams subscriber.
     */
    public Subscriber<T> getReactiveSubscriber() {
        return getLazyReactiveSubscriber();
    }

    /**
     * Creates a new cross subscriber.
     * @param flowSubscriber Flow subscriber.
     * @return Created cross subscriber.
     * @param <T> Item type.
     */
    public static <T> CrossSubscriber<T> of(Flow.Subscriber<T> flowSubscriber) {
        return of(flowSubscriber,null);
    }

    /**
     * Creates a new cross subscriber.
     * @param reactiveSubscriber Reactive Streams subscriber.
     * @return Created cross subscriber.
     * @param <T> Item type.
     */
    public static <T> CrossSubscriber<T> of(Subscriber<T> reactiveSubscriber) {
        return of(null,reactiveSubscriber);
    }
}
