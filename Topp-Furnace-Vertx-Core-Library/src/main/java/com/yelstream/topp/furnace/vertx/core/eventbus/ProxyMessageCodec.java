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

package com.yelstream.topp.furnace.vertx.core.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor//(staticName="of")
public class ProxyMessageCodec<S,R> implements MessageCodec<S,R> {

    private final String name;

    private final MessageCodec<S,R> codec;

    @Override
    public void encodeToWire(Buffer buffer,
                             S s) {
        codec.encodeToWire(buffer,s);
    }

    @Override
    public R decodeFromWire(int pos,
                            Buffer buffer) {
        return codec.decodeFromWire(pos,buffer);
    }

    @Override
    public R transform(S s) {
        return codec.transform(s);
    }

    @Override
    public String name() {
        return name!=null?name:codec.name();
    }

    @Override
    public byte systemCodecID() {
        return codec.systemCodecID();
    }
}
