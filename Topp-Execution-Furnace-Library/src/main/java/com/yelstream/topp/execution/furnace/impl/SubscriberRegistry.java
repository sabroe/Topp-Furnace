package com.yelstream.topp.execution.furnace.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;

public class SubscriberRegistry<T,S extends Flow.Subscriber<T>,P extends Flow.Subscription> {

    private final Map<S,SubscriberInfo<T,S,P>> info=new ConcurrentHashMap<>();

    public int getSubscriberCount() {
        return info.size();
    }

    public void add(S subscriber) {
        // TODO document why this method is empty
    }

    public void remove(S subscriber) {
        // TODO document why this method is empty
    }

}
