package com.yelstream.topp.execution.concurrent.zoom;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface InvokeManager {

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException;

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout,
                                  TimeUnit unit)
            throws InterruptedException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException,
            ExecutionException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout,
                    TimeUnit unit)
            throws InterruptedException,
            ExecutionException,
            TimeoutException;

}
