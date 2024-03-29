package com.yelstream.topp.execution.furnace.processor;

import java.util.concurrent.Flow;

public class SimpleProcessor<T,R> implements Flow.Subscriber<T>, Flow.Publisher<R> {

    private Flow.Subscriber<? super R> subscriber;

    private Flow.Subscription subscription;

    @Override
    public void subscribe(Flow.Subscriber<? super R> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // Request the first element when subscribed
    }

    @Override
    public void onNext(T item) {
        // Perform computation on the item, for example, multiply it by 2
        int result = item * 2;

        // Pass the computed result downstream
        subscriber.onNext(result);

        // Request the next element
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        // Handle errors if any
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        // Complete the subscriber when the computation is done
        subscriber.onComplete();
    }
}
