package com.yelstream.topp.furnace.manage;

public interface SpawnManager<S extends Stoppable<T,E>,T,E extends Exception> extends Startable<S,E>, AutoCloseable {

    @Override
    abstract void close() throws E;
}
