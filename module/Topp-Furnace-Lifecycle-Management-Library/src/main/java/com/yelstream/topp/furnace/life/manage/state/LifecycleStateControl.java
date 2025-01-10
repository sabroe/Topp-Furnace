/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.furnace.life.manage.state;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-03
 */
public class LifecycleStateControl {  //TO-DO: Consider generalization, "StateControl" with a generic type.
    /**
     *
     */
    private final AtomicReference<LifecycleState> stateReference=new AtomicReference<>();

    /**
     *
     */
    private final BiConsumer<LifecycleState,LifecycleState> stateChangeConsumer;

    public LifecycleStateControl(LifecycleState state,
                                 BiConsumer<LifecycleState,LifecycleState> stateChangeConsumer) {
        this.stateReference.set(state);
        this.stateChangeConsumer=stateChangeConsumer;
    }

    public LifecycleStateControl(LifecycleState state) {
        this(state,null);
    }

    public LifecycleStateControl() {
        this(LifecycleState.NOT_RUNNING,null);
    }

    public final boolean compareAndSet(LifecycleState expectedValue, LifecycleState newValue) {  //TO-DO: Consider synchronization!
        boolean b=stateReference.compareAndSet(expectedValue,newValue);
        if (b) {
            if (stateChangeConsumer!=null) {
                stateChangeConsumer.accept(expectedValue,newValue);
            }
        }
        return b;
    }

    public void setState(LifecycleState state) {
        stateReference.set(state);
    }

    public LifecycleState getState() {
        return stateReference.get();
    }
}
