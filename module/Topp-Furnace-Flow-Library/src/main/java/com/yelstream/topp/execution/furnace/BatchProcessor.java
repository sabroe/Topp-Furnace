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

package com.yelstream.topp.execution.furnace;

import java.util.concurrent.Flow;

public class BatchProcessor<T,R> implements Flow.Processor<T,R> {
/*
    Batch Processor:

    A batch processor is a common type of processor that aggregates multiple individual events into a single batch before processing or emitting them.
    It collects events over a certain time window or until a specified number of events is reached, then processes the batch as a whole.
    Batch processing can help reduce overhead by minimizing the number of individual processing steps and can improve efficiency, especially in scenarios where handling individual events separately is not necessary.
*/


/*
Batch Processor vs. Windowing Processor:

Batch Processor: A Batch Processor aggregates multiple individual events into a single batch before processing or emitting them. It typically collects events over a certain time window or until a specified number of events is reached, then processes the batch as a whole. Batch processing helps reduce overhead and improve efficiency by minimizing the number of individual processing steps.
Windowing Processor: A Windowing Processor segments the stream of events into discrete time-based or count-based windows. It can compress multiple events within each window into a summary or aggregated result, such as computing averages, sums, or counts. While windowing processors can include batch processing as part of their functionality, they can also perform other types of aggregations or operations within each window.
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
