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

package com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Provides setting and preferences for how to access into the data space of a buffer.
 * <p>
 *     This is intended to be used in cooperation with {@link Space}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@Getter
@Setter
@AllArgsConstructor
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class Slide {
    /**
     * Default character-set.
     */
    public static final Charset DEFAULT_CHARSET=StandardCharsets.UTF_8;

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
}
