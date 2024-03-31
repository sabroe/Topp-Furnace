package com.yelstream.topp.execution.concurrent.zoom;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface SubmitManager {



    Future<?> submit(Runnable task);

    <T> Future<T> submit(Runnable task,
                         T result);

    <T> Future<T> submit(Callable<T> task);

}
