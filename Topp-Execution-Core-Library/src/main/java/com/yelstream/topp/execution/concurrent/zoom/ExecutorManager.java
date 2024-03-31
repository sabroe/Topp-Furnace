package com.yelstream.topp.execution.concurrent.zoom;

public interface ExecutorManager extends AutoCloseable {

    void execute(Runnable command);

}
