package com.yelstream.topp.execution.furnace;

import java.util.concurrent.Flow;

public class FanoutProcessor<T,R> implements Flow.Processor<T,R> {

/*
    Fan-out Processor:

    A fan-out processor duplicates incoming events and sends copies to multiple downstream processors or subscribers.
    It allows for parallel processing or distribution of events across multiple processing units or systems.
    Fan-out processors are commonly used in messaging systems or distributed computing frameworks to scale out processing and increase throughput.
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
