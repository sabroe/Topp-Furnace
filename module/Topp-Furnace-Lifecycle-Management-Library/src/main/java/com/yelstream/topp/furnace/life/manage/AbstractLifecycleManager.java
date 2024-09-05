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

package com.yelstream.topp.furnace.life.manage;

import com.yelstream.topp.furnace.life.manage.op.Startable;
import com.yelstream.topp.furnace.life.manage.op.StateSafeStartable;
import com.yelstream.topp.furnace.life.manage.op.StateSafeStoppable;
import com.yelstream.topp.furnace.life.manage.op.Stoppable;
import com.yelstream.topp.furnace.life.manage.state.LifecycleState;
import com.yelstream.topp.furnace.life.manage.state.LifecycleStateControl;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-03
 */
public abstract class AbstractLifecycleManager<S,T,E extends Exception> implements LifecycleManager<S,T,E> {
    /**
     *
     */
    private final LifecycleStateControl stateControl;

    /**
     *
     */
    private final Startable<S,E> startable;

    /**
     *
     */
    private final Stoppable<T,E> stoppable;

    @Getter
    private S deployment;  //TO-DO: What is good naming? Runnable-reference, a handle, not the runnable itself!?

    @Override
    public final LifecycleState getState() {
        return stateControl.get();
    }

    @Override
    public final CompletableFuture<S> start() throws E {
        return startable.start().thenApply(res->deployment=res);
    }

    @Override
    public final CompletableFuture<T> stop() throws E {
        return stoppable.stop().thenApply(res->{ deployment=null; return res; });
    }

    protected AbstractLifecycleManager(Startable<S,E> startable,
                                       Stoppable<T,E> stoppable) {
        this.stateControl=new LifecycleStateControl(LifecycleState.NOT_RUNNING);
        this.startable=StateSafeStartable.of(stateControl,startable);
        this.stoppable=StateSafeStoppable.of(stateControl,stoppable,LifecycleState.NOT_RUNNING);
    }
}
