package com.yelstream.topp.execution.furnace;

import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class ExecutorPublisher<T> implements Flow.Publisher<T> {

//    private final SubscriberRegistry<T,BaseSubscriber<T,Flow.Subscriber<T>>, BaseSubscription<Flow.Subscription>> subscriberRegistry=new SubscriberRegistry();



    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        // TODO document why this method is empty
    }

    public Future<T> submit(T item) {
        return null;
    }

}
