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

package com.yelstream.topp.furnace.vertx.core;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.ToIntFunction;

/**
 * Buffered handler recognizing message limits.
 * <p>
 *     This is not thread-safe, does not need to be thread-safe.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@Slf4j
@RequiredArgsConstructor
public class BufferedHandler implements Handler<Buffer> {
    /**
     * Indicates, if a buffer contains one or more messages.
     * A positive indication is given by a non-null result indicating the number of bytes within recognized messages.
     */
    private final ToIntFunction<Buffer> processBuffer;

    /**
     * Buffer accumulating received data.
     * This is used for message recognition.
     */
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
