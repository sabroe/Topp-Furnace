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

package com.yelstream.topp.furnace.life.manage;

import com.yelstream.topp.furnace.life.manage.op.Startable;
import com.yelstream.topp.furnace.life.manage.op.Stoppable;
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link Manageable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-27
 */@UtilityClass
public class Manageables {

/*
    public static <S,T,E extends Exception,M extends LifecycleManager<S,T,E>> Manageable<S,T,E,M> createManageable(Startable<S,E> startable,
                                                                                                                   Stoppable<T,E> stoppable) {
        LifecycleManager<S,T,E> manager=ComposedLifecycleManager.of(startable,stoppable);
        return ()->manager;
    }
*/

    public static <S,T,E extends Exception> Manageable<S,T,E,LifecycleManager<S,T,E>> createManageable(Startable<S,E> startable,
                                                                                                       Stoppable<T,E> stoppable) {
return null;
/*
        LifecycleManager<S,T,E> manager=ComposedLifecycleManager.of(startable,stoppable);
        return ()->manager;
*/
    }

    public static <S,T,E extends Exception,M extends LifecycleManager<S,T,E>> void close(Manageable<S,T,E,M> manageable) {
        try {
            M manager=manageable.getManager();
            manager.stop().join();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to close managed component!",ex);
        }
    }
}
