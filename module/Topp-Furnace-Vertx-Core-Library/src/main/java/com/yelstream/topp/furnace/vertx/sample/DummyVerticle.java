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

package com.yelstream.topp.furnace.vertx.sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@AllArgsConstructor
public class DummyVerticle extends AbstractVerticle {

    public static final Duration DEFAULT_DELAY=Duration.ofSeconds(5L);

    private final Duration startDelay;
    private final Duration stopDelay;

    @Override
    public void start(Promise<Void> startPromise) {
        Duration delay=startDelay==null?DEFAULT_DELAY:startDelay;
        log.atDebug().setMessage("Start initiated, delay is {}.").addArgument(delay).log();
        vertx.setTimer(delay.toMillis(), id -> {
            log.atDebug().setMessage("Start completed.").log();
            startPromise.complete();
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        Duration delay=stopDelay==null?DEFAULT_DELAY:stopDelay;
        log.atDebug().setMessage("Stop initiated, delay is {}.").addArgument(delay).log();
        vertx.setTimer(delay.toMillis(), id -> {
            log.atDebug().setMessage("Stop completed.").log();
            stopPromise.complete();
        });
    }
}
