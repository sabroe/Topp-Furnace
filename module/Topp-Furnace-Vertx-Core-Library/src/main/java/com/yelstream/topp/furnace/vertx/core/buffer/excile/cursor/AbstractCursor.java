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
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
public abstract class AbstractCursor<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements Cursor<C,R,W> {
    /**
     *
     */
    protected CursorState state;

    protected AbstractCursor(CursorState state) {
        this.state=state;
    }

    protected AbstractCursor(Supplier<Gettable> gettableSupplier,
                             Supplier<Puttable> puttableSupplier) {
        this.state=CursorState.builder().gettableSupplier(gettableSupplier).puttableSupplier(puttableSupplier).build();
    }
}
