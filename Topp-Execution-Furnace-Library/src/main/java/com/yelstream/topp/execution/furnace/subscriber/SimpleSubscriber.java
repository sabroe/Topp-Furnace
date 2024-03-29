package com.yelstream.topp.execution.furnace.subscriber;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor(staticName="of")
public class SimpleSubscriber<T,R> implements Flow.Subscriber<T> {

    private Flow.Subscriber<? super R> subscriber;  //TODO: Make this a subscriber registry!

    private final int initialRequestCount=1;

    private Flow.Subscription subscription;

    private final Function<T,R> computation;

    private final BiConsumer<R,Flow.Subscriber<? super R>> resultSubmit;

    private final BiConsumer<R,Flow.Subscription> request;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription=subscription;
        subscription.request(initialRequestCount);
    }

    @Override
    public void onNext(T item) {
        // Perform computation on the item, for example, multiply it by 2
        //int result = item * 2;
        R result=computation.apply(item);

        // Pass the computed result downstream
        //subscriber.onNext(result);
        resultSubmit.accept(result,subscriber);

        // Request the next element
        //subscription.request(1);
        request.accept(result,subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
