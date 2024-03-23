package com.yelstream.topp.execution.furnace.processor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;

@RequiredArgsConstructor(staticName="of")
public class TypedProcessor<R,T,P extends Flow.Publisher<R>,S extends Flow.Subscriber<T>,H> implements Flow.Processor<T,R> {
    private final H tie;

    @Getter
    private final P publisher;

    @Getter
    private final S subscriber;

    @Override
    public void subscribe(Flow.Subscriber<? super R> subscriber) {
        publisher.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T item) {
        subscriber.onNext(item);
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
