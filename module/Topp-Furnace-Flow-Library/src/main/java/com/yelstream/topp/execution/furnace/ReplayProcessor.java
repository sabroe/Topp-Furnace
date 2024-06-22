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

package com.yelstream.topp.execution.furnace;

import java.util.concurrent.Flow;

public class ReplayProcessor<T,R> implements Flow.Processor<T,R> {
/*
    Replay Processor:

    A replay processor replicates events from a source to one or more downstream consumers.
    It captures events as they occur and replays them to downstream consumers, ensuring that all subscribers receive the same events in the same order.
    Replay processors are useful for scenarios where it's essential to replicate events to multiple consumers for redundancy, fault tolerance, or parallel processing.
*/

/*
    Replay Processor vs. Fan-out Processor:

    Replay Processor: A Replay Processor replicates events from a source to one or more downstream consumers. It captures events as they occur and replays them to downstream consumers, ensuring that all subscribers receive the same events in the same order. Replay processors are focused on ensuring data consistency and fault tolerance by replicating events.
    Fan-out Processor: A Fan-out Processor duplicates incoming events and sends copies to multiple downstream processors or subscribers. It allows for parallel processing or distribution of events across multiple processing units or systems. Fan-out processors focus on scaling out processing and increasing throughput by distributing events to multiple consumers.
 */


    @Override
    public void subscribe(Flow.Subscriber<? super R> subscriber) {

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(T item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
