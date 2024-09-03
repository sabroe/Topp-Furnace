/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.furnace.life.manage.op;

import com.yelstream.topp.furnace.life.manage.state.LifecycleState;
import com.yelstream.topp.furnace.life.manage.state.LifecycleStateControl;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @param <T> Type of result.
 * @param <E> Type of exception.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-03
 */
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class StateSafeStoppable<T,E extends Exception> implements Stoppable<T,E> {
    /**
     *
     */
    private final LifecycleStateControl stateControl;

    /**
     *
     */
    private final Stoppable<T,E> stoppable;

    /**
     *
     */
    @lombok.Builder.Default
    private final LifecycleState failState=LifecycleState.NOT_RUNNING;

    @Override
    public CompletableFuture<T> stop() throws E {  //TO-DO: Consider allowing for a 2. and 3. invocation, returning the same future as on the 1. invocation!
        LifecycleState initialState=LifecycleState.RUNNING;
        if (!stateControl.compareAndSet(initialState,LifecycleState.STOPPING)) {
            return CompletableFuture.failedFuture(new IllegalStateException(String.format("Failure to initiate stop; state must be %s!",initialState)));
        } else {
            return stoppable.stop().whenComplete((value,ex)->{
                LifecycleState newState;
                if (ex!=null) {
                    newState=failState;
                } else {
                    newState=LifecycleState.NOT_RUNNING;
                }
                stateControl.compareAndSet(LifecycleState.STOPPING,newState);
            });
        }
    }
}
