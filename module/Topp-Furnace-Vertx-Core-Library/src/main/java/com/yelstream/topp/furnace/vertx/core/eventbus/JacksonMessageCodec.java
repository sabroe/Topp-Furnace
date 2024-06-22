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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Function;

/**
 * Eventbus message codec for messages in JSON and implemented with Jackson.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@Slf4j
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class JacksonMessageCodec<S,R> implements MessageCodec<S,R> {

    public static final Charset NORMATIVE_JSON_CHARSET=StandardCharsets.UTF_8;

    @lombok.Builder.Default
    private final String name=String.format("%s//%s",JacksonMessageCodec.class.getName(),UUID.randomUUID().toString().toLowerCase());

    private final Class<S> sClass;

    private final Class<R> rClass;

    private final Function<S,R> transformation;

    @lombok.Builder.Default
    private final ObjectMapper objectMapper=new ObjectMapper();

    @lombok.Builder.Default
    private final Charset charset=NORMATIVE_JSON_CHARSET;

    @Override
    public void encodeToWire(Buffer buffer,
                             S s) {
        String value;
        try {
            value=objectMapper.writeValueAsString(s);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(String.format("Failure to convert JSON object to textual value; object is %s!",s),ex);
        }
        buffer.appendString(value,NORMATIVE_JSON_CHARSET.name());
    }

    @Override
    public R decodeFromWire(int pos,
                            Buffer buffer) {
        Buffer b=buffer.slice(pos,buffer.length());
        String value=new String(b.getBytes(),NORMATIVE_JSON_CHARSET);
        R r;
        try {
            r=objectMapper.readValue(value,rClass);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(String.format("Failure to convert textual value to JSON object; value is %s!",value),ex);
        }
        return r;
    }

    @Override
    public R transform(S s) {
        return transformation.apply(s);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

    private static <M> Builder<M,M> builderOneType(Class<M> clazz) {
        Builder<M,M> builder=builder();
        builder.sClass(clazz).rClass(clazz).transformation(Function.identity());
        return builder;
    }

    public static <M> JacksonMessageCodec<M,M> of(String name,
                                                  Class<M> clazz,
                                                  ObjectMapper objectMapper) {
        Builder<M,M> builder=builderOneType(clazz);
        builder.name(name);
        builder.objectMapper(objectMapper);
        return builder.build();
    }

    public static <M> JacksonMessageCodec<M,M> of(Class<M> clazz,
                                                  ObjectMapper objectMapper) {
        Builder<M,M> builder=builderOneType(clazz);
        builder.objectMapper(objectMapper);
        return builder.build();
    }

    public static <M> JacksonMessageCodec<M,M> of(Class<M> clazz) {
        Builder<M,M> builder=builderOneType(clazz);
        return builder.build();
    }
}
