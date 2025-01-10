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

package com.yelstream.topp.furnace.pulse.signal.codec;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName="of")
public class SimpleCodec<S,X,T> implements Codec<S,X,T> {

    @Getter
    private final String name;

    @Getter
    private final Encoder<S,X> encoder;

    @Getter
    private final Decoder<X,T> decoder;
}
