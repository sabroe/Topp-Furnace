package com.yelstream.topp.execution.concurrent.flow;

import java.util.concurrent.Flow;

/**
 * Publisher specific for virtual threads.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-23
 *
 * @param <T> Type of work item.
 */
public abstract class VirtualPublisher<T> implements Flow.Publisher<T> {
}
