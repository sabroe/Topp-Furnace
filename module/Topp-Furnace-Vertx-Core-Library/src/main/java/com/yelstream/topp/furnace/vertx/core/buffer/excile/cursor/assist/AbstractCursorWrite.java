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

package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.assist;

import com.google.common.io.CountingOutputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorRead;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.CursorWrite;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.PuttableOutputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Slide;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Space;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor
public abstract class AbstractCursorWrite<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorWrite<C,R,W> {
    /**
     *
     */
    private final C cursor;

    /**
     * Buffer access.
     */
    protected final Space space;

    /**
     * Settings for indexing into buffer.
     */
    protected final Slide slide;

    /**
     *
     */
    protected abstract W self();

    @Override
    public W outputStream(ConsumerWithException<OutputStream,IOException> consumer) {
        //TO-DO: Fix!
        return self();
    }

    @Override
    public W writer(ConsumerWithException<Writer,IOException> consumer) {
        //TO-DO: Fix!
        return self();
    }

    @Override
    public W printStream(Consumer<PrintStream> consumer) {
        //TO-DO: Fix!
        return self();
    }

    @Override
    public W byteBuffer(Consumer<ByteBuffer> consumer) {
        //TO-DO: Fix!
        return self();
    }

    @Override
    public W charBuffer(Consumer<CharBuffer> consumer) {
        //TO-DO: Fix!
        return self();
    }

    @Override
    public W stringBuilder(Consumer<StringBuilder> consumer) {
        int index=slide.getIndex();
        Charset charset=slide.getCharset();
        StringBuilder sb=new StringBuilder();
        consumer.accept(sb);
        String builtString=sb.toString();
        byte[] bytes=builtString.getBytes(charset==null?StandardCharsets.UTF_8:charset);
        space.getPuttable().put(index,bytes);
        slide.setIndex(index+bytes.length);
        return self();
    }

    @Override
    public W dataOutput(ConsumerWithException<DataOutput,IOException> consumer) {
        int index=slide.getIndex();
        try (OutputStream outputStream=new PuttableOutputStream(space.getPuttable(),index);
             CountingOutputStream countingOutputStream=new CountingOutputStream(outputStream);
             DataOutputStream dataOutputStream=new DataOutputStream(countingOutputStream)) {
            consumer.accept(dataOutputStream);
            slide.setIndex(index+(int)countingOutputStream.getCount());
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return self();
    }

    @Override
    public C end() {
        return cursor;
    }
}
