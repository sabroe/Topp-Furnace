package com.yelstream.topp.furnace.pulse.signal;

public interface Route {

    boolean local();

    boolean intraProcess();

    boolean interProcess();

}
