package com.yelstream.topp.furnace.pulse.signal;

public interface Endpoint {


    interface Consumer {}
    interface Producer {}
    Consumer consumer();
    Producer producer();

}
