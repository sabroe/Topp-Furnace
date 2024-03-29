package com.yelstream.topp.execution.furnace.simple;

import java.util.concurrent.Flow.*;

public class ComputationProcessor implements Processor<Integer, Integer> {

    private Subscriber<? super Integer> subscriber;
    private Subscription subscription;

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // Request the first element when subscribed
    }

    @Override
    public void onNext(Integer item) {
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
