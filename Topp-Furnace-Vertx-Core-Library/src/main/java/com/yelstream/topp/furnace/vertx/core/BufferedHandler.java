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

package com.yelstream.topp.furnace.vertx.core;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.ToIntFunction;


@Slf4j
@RequiredArgsConstructor
public class BufferedHandler implements Handler<Buffer> {

    private final ToIntFunction<Buffer> processBuffer;

    private Buffer buffer=Buffer.buffer();

    @Override
    public void handle(Buffer data) {
        buffer.appendBuffer(data);
        int count=processBuffer.applyAsInt(buffer);
        if (count>0) {
            buffer=buffer.getBuffer(count,buffer.length());
        }
    }
}
