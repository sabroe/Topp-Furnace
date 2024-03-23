package com.yelstream.topp.execution.furnace;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Flow;

public class SynchronousPublisher<T> implements Flow.Publisher<T> {

    private final List<Flow.Subscriber<? super T>> subscribers=new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {

    }

    public void submit(T item) {
    }


}
