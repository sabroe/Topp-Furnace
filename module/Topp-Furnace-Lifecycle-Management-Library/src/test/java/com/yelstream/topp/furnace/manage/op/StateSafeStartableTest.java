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

package com.yelstream.topp.furnace.manage.op;

import com.yelstream.topp.furnace.life.manage.op.Startable;
import com.yelstream.topp.furnace.life.manage.op.StateSafeStartable;
import com.yelstream.topp.furnace.life.manage.state.LifecycleState;
import com.yelstream.topp.furnace.life.manage.state.LifecycleStateControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Test of {@link StateSafeStartable}.
 */
class StateSafeStartableTest {
    /**
     * Tests a normal start by {@link StateSafeStartable#start()}.
     * @throws Exception Thrown in case of error.
     */
    @Test
    void plainStart() throws Exception {
        String producedResult=UUID.randomUUID().toString();
        Startable<String,Exception> startable=()->CompletableFuture.completedFuture(producedResult);

        LifecycleStateControl control=new LifecycleStateControl();
        StateSafeStartable<String,Exception> stateSafeStartable=StateSafeStartable.of(control,startable);

        CompletableFuture<String> future=stateSafeStartable.start();
        String result=future.get();

        Assertions.assertEquals(result,producedResult);
    }

    /**
     * Tests an abnormal start by {@link StateSafeStartable#start()}, where the state differs from {@link LifecycleState#NOT_RUNNING}.
     * @throws Exception Thrown in case of error.
     */
    @ParameterizedTest
    @EnumSource(value=LifecycleState.class,
                names={"STARTING","RUNNING","STOPPING"})
    void nonPlainStart(LifecycleState state) throws Exception {
        String producedResult=UUID.randomUUID().toString();
        Startable<String,Exception> startable=()->CompletableFuture.completedFuture(producedResult);

        LifecycleStateControl control=new LifecycleStateControl(state);  //Any state different from NON_RUNNING!
        StateSafeStartable<String,Exception> stateSafeStartable=StateSafeStartable.of(control,startable);

        CompletableFuture<String> future=stateSafeStartable.start();
        Throwable exception=null;
        try {
            future.get();
        } catch (Exception ex) {
            exception=ex;
        }
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(ExecutionException.class,exception.getClass());
        Assertions.assertNotNull(exception.getCause());
        Assertions.assertEquals(IllegalStateException.class,exception.getCause().getClass());
        Assertions.assertTrue(exception.getCause().getMessage().startsWith("Failure to initiate start"));  //Yes, this is an implicit check of the source of the exception!
    }

    /**
     * Tests an abnormal start by {@link StateSafeStartable#start()}., where the contained source fails.
     * @throws Exception Thrown in case of error.
     */
    @Test
    void exceptionalStart() throws Exception {
        Startable<String,Exception> startable=()->CompletableFuture.failedFuture(new Exception("Argh!"));  //Any type different from 'IllegalStateException'!

        LifecycleStateControl control=new LifecycleStateControl();
        StateSafeStartable<String,Exception> stateSafeStartable=StateSafeStartable.of(control,startable);

        CompletableFuture<String> future=stateSafeStartable.start();
        Throwable exception=null;
        try {
            future.get();
        } catch (Exception ex) {
            exception=ex;
        }
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(ExecutionException.class,exception.getClass());
        Assertions.assertNotNull(exception.getCause());
        Assertions.assertEquals(Exception.class,exception.getCause().getClass());
        Assertions.assertEquals("Argh!",exception.getCause().getMessage());  //Yes, this is an implicit check of the source of the exception!
    }
}
