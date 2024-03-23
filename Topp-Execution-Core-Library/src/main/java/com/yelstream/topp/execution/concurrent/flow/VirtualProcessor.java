package com.yelstream.topp.execution.concurrent.flow;

import java.util.concurrent.Flow;

/**
 * Processor specific for virtual threads.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 *
 * @param <T> Type of subscribed work item.
 * @param <R> Type of published work item.
 */
public abstract class VirtualProcessor<T,R> implements Flow.Processor<T,R> {
}
