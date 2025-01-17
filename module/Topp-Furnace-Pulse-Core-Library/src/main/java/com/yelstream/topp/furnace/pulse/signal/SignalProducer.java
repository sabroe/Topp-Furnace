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

package com.yelstream.topp.furnace.pulse.signal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

/**
 * Consumes signals from a beacon.
 * @param <S> Type of signal.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-09
 */
public interface SignalProducer<S> {  //TO-DO: Consider AutoClosable!

    //TO-DO: Get address-information!

    //TO-DO: Get producer-options!

    /**
     *
     * @param signal
     * @return
     */
    CompletableFuture<Void> write(S signal);

    /**
     *
     * @return
     */
    CompletableFuture<Void> close();

    /**
     * Gets a subscriber which can subscribe from a publisher to send events.
     * @return Subscriber to which a publisher can send events.
     */
    Flow.Subscriber<S> getSubscriber();
}
