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

package com.yelstream.topp.furnace.vertx.core.buffer;

import com.google.common.primitives.Bytes;
import lombok.experimental.UtilityClass;

import io.vertx.core.buffer.Buffer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utility addressing {@link Buffer} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-30
 */
@UtilityClass
public class Buffers {

    public static final Charset DEFAULT_CHARSET=StandardCharsets.UTF_8;

    public static int indexOf(Buffer buffer,
                              byte[] match) {
        byte[] bytes=buffer.getBytes();
        return Bytes.indexOf(bytes,match);
    }

    public static String getText(Buffer buffer,
                                 int index,
                                 int length,
                                 String characterEncoding) {
        return buffer.getString(index,index+length,characterEncoding);
    }

    public static String getText(Buffer buffer,
                                 int index,
                                 int length,
                                 Charset charset) {
        return getText(buffer,index,length,charset.name());
    }

    public static String getText(Buffer buffer,
                                 int index,
                                 int length) {
        return getText(buffer,index,length,DEFAULT_CHARSET);
    }

    public static int expectText(Buffer buffer,
                                 int index,
                                 String expectedValue,
                                 String characterEncoding) {
        String actualValue=getText(buffer,index,expectedValue.length(),characterEncoding);
        if (!expectedValue.equals(actualValue)) {
            throw new IllegalStateException(String.format("Failure to verify expected value; expected value was %s, actual value is %s!",expectedValue,actualValue));
        }
        return expectedValue.length();
    }

    public static int expectText(Buffer buffer,
                                 int index,
                                 String expectedValue,
                                 Charset charset) {
        return expectText(buffer,index,expectedValue,charset.name());
    }

    public static int expectText(Buffer buffer,
                                 int index,
                                 String expectedValue) {
        return expectText(buffer,index,expectedValue,DEFAULT_CHARSET);
    }


    public static boolean isAtEndOfBuffer(Buffer buffer,
                                         int index) {
        int length=buffer.length();
        return index==length;
    }

    public static void verifyAtEndOfBuffer(Buffer buffer,
                                           int index) {
        if (!isAtEndOfBuffer(buffer,index)) {
            throw new IllegalStateException(String.format("Failure to verify that index is at the end of the buffer; index is %d, length is %d!",index,buffer.length()));
        }
    }
}
