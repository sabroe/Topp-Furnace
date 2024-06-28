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

package com.yelstream.topp.furnace.reactive.integration.reactivestream.cross;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Publisher;

import java.util.concurrent.Flow;

/**
 * Associates different publishers, a Flow publisher and a Reactive Streams publisher, one wrapping the other.
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
public class CrossPublisher<T> {
/*
    public enum Capability {
        SINGLE_ITEM,
        MULTI_ITEM
    }

    private final Capability capability;  //TO-DO: Consider adding this!
*/

    /**
     * Flow publisher.
     */
    private final Flow.Publisher<T> flowPublisher;

    /**
     * Reactive Streams publisher.
     */
    private final Publisher<T> reactivePublisher;

    /**
     * Actually used Flow publisher, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Flow.Publisher<T> lazyFlowPublisher=initFlowPublisher();

    /**
     * Actually used Reactive Streams publisher, possibly wrapped.
     */
    @Getter(lazy=true)
    private final Publisher<T> lazyReactivePublisher=initReactivePublisher();

    /**
     * Initializes the used Flow publisher.
     * @return Flow publisher.
     */
    private Flow.Publisher<T> initFlowPublisher() {
        if (flowPublisher!=null) {
            return flowPublisher;
        } else {
            if (reactivePublisher==null) {
                return null;
            } else {
                return FlowAdapters.toFlowPublisher(reactivePublisher);
            }
        }
    }

    /**
     * Initializes the used Reactive Streams publisher.
     * @return Reactive Streams publisher.
     */
    private Publisher<T> initReactivePublisher() {
        if (reactivePublisher!=null) {
            return reactivePublisher;
        } else {
            if (flowPublisher==null) {
                return null;
            } else {
                return FlowAdapters.toPublisher(flowPublisher);
            }
        }
    }

    /**
     * Gets the Flow publisher.
     * @return Flow publisher.
     */
    public Flow.Publisher<T> getFlowPublisher() {
        return getLazyFlowPublisher();
    }

    /**
     * Gets the Reactive Streams publisher.
     * @return Reactive Streams publisher.
     */
    public Publisher<T> getReactivePublisher() {
        return getLazyReactivePublisher();
    }

    /**
     * Creates a new cross publisher.
     * @param flowPublisher Flow publisher.
     * @return Created cross publisher.
     * @param <T> Item type.
     */
    public static <T> CrossPublisher<T> of(Flow.Publisher<T> flowPublisher) {
        return of(flowPublisher,null);
    }

    /**
     * Creates a new cross publisher.
     * @param reactivePublisher Reactive Streams publisher.
     * @return Created cross publisher.
     * @param <T> Item type.
     */
    public static <T> CrossPublisher<T> of(Publisher<T> reactivePublisher) {
        return of(null,reactivePublisher);
    }
}
