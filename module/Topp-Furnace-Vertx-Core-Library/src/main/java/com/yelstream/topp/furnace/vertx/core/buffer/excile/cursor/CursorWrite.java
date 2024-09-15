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

import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.function.Consumer;

public interface CursorWrite<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> {

    W outputStream(ConsumerWithException<OutputStream,IOException> consumer);

    W writer(ConsumerWithException<Writer,IOException> consumer);

    W printStream(Consumer<PrintStream> consumer);

    W byteBuffer(Consumer<ByteBuffer> consumer);

    W charBuffer(Consumer<CharBuffer> consumer);

    /**
     * Uses a StringBuilder to build a string by applying the provided Consumer function.
     * The content of the StringBuilder is then written back to the buffer, and the cursor index is updated.
     *
     * @param consumer A Consumer function that receives the StringBuilder for building the string.
     * @return The BufferCursor instance, with the index updated to the end of the newly written content.
     */
    W stringBuilder(Consumer<StringBuilder> consumer);

    W dataOutput(ConsumerWithException<DataOutput,IOException> consumer);

    C end();
}
