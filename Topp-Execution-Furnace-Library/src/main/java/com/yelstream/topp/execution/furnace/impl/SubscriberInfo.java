package com.yelstream.topp.execution.furnace.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;

@RequiredArgsConstructor
public class SubscriberInfo<T,S extends Flow.Subscriber<T>, P extends Flow.Subscription> {
    @Getter
    private final S subscriber;

    @Getter
    private final P subscription;

}
