package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.manage.op.Startable;
import com.yelstream.topp.furnace.manage.op.Stoppable;
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link Processable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-27
 */@UtilityClass
public class Processables {

    public static <S,T,E extends Exception> Manageable<S,T,E> createManageable(Startable<S,E> startable,
                                                                               Stoppable<T,E> stoppable) {
        LifecycleManager<S,T,E> manager=ComposedLifecycleManager.of(startable,stoppable);
        return ()->manager;
    }
}
