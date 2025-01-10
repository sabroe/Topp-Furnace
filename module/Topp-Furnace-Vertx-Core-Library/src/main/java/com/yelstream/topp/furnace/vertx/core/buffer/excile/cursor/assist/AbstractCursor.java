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

package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.assist;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.Cursor;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorRead;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorWrite;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Slide;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Space;
import lombok.AllArgsConstructor;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor
public abstract class AbstractCursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements Cursor<C,R,W> {
    /**
     * Buffer access.
     */
    protected final Space space;

    /**
     * Settings for indexing into buffer.
     */
    protected final Slide slide;

    protected AbstractCursor(Space space) {
        this.space=space;
        this.slide=Slide.builder().build();
    }
}
