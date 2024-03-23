package com.yelstream.topp.execution.furnace;

import java.util.concurrent.Flow;

public class WindowingProcessor<T,R> implements Flow.Processor<T,R> {
/*
Windowing Processor:

Windowing processors segment the stream of events into discrete time-based or count-based windows.
These processors can compress multiple events within each window into a summary or aggregated result, such as computing averages, sums, or counts.
Windowing processors are commonly used in stream processing frameworks like Apache Beam, Apache Flink, or Kafka Streams to perform time-based or count-based aggregations over streaming data.
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
