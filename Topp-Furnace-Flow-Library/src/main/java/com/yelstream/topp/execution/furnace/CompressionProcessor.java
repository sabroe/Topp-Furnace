package com.yelstream.topp.execution.furnace;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class CompressionProcessor<T> implements Flow.Publisher<T> {

/*
    private final SubscriberRegistry<T, BaseSubscriber<T,Flow.Subscriber<T>>, BaseSubscription<Flow.Subscription>> subscriberRegistry=new SubscriberRegistry();

    private final Function<T,String> itemToCategoryNameMapper;

    private final BiFunction<T,T,T> itemCompressor;

    private class Category {
        //Queue
    }

    private Map<String,Category> categories=new ConcurrentHashMap<>();
*/


    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        // TODO document why this method is empty
    }

    public Future<T> submit(T item) {
        return null;
    }


}
