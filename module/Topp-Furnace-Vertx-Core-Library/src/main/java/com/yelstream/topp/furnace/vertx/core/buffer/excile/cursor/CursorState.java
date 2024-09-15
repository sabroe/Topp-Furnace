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

package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@Getter
@Setter
@AllArgsConstructor
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class CursorState {
    /**
     * Default character-set.
     */
    public static final Charset DEFAULT_CHARSET=StandardCharsets.UTF_8;

    /**
     *
     */
    private final Supplier<Gettable> gettableSupplier;  //TO-DO: Consider if indirect supplier is necessary!

    /**
     *
     */
    private final Supplier<Puttable> puttableSupplier;  //TO-DO: Consider if indirect supplier is necessary!

    /**
     * Current index into the Vert.x buffer.
     */
    @lombok.Builder.Default
    private int index=0;

    /**
     * Character-set to use, if doing textual parsing.
     */
    @lombok.Builder.Default
    private Charset charset=DEFAULT_CHARSET;

    /**
     *
     */
    @lombok.Builder.Default
    private ByteOrder byteOrder=null;

    /**
     *
     */
    @lombok.Builder.Default
    private Locale locale=null;

    /*
        TO-DO: Split this 'CursorState' into two parts:
            -- User settings:
                 -- Charset
                 -- Byteorder
                 -- Locale
                 -- Possibly the index into the buffer too!
            -- Plain buffer access:
                 -- Get-length
                 -- Expand-functionality
                 -- Slice-functionality
                 -- Verification functionality, e.g. #isAtEndOfBuffer()
            How about ... 'CursorSettting' and 'BufferAccess'?
            Note that the purpose of 'BufferAccess' is to abstract away specific buffers while still have access to length, slice, etc -- and wrap Gettable and Puttable.
            Note that the purpose of 'CursorSetting' is to handle the choices of the user (better naming, it becomes an interpretation-setting!).
     */
}
