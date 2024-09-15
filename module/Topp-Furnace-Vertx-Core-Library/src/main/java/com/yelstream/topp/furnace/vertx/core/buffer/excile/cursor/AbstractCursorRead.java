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

import com.google.common.io.CountingInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.cursor.BufferCursor;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.GettableInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongSupplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor
public abstract class AbstractCursorRead<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorRead<C,R,W> {
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    protected final Gettable gettable;

    /**
     *
     */
    protected final CursorState state;

    /**
     *
     */
    protected abstract R getThis();

    @Override
    public R inputStream(ConsumerWithException<InputStream,IOException> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public R reader(ConsumerWithException<Reader,IOException> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public R byteBuffer(Consumer<ByteBuffer> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public R charBuffer(Consumer<CharBuffer> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public R dataInput(ConsumerWithException<DataInput,IOException> consumer) {
        int index=state.getIndex();
        try (InputStream inputStream=new GettableInputStream(gettable,index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             DataInputStream dataInputStream=new DataInputStream(countingInputStream)) {
            consumer.accept(dataInputStream);
            state.setIndex(index+(int)countingInputStream.getCount());
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return getThis();
    }

    @RequiredArgsConstructor
    private static class SimpleLookahead implements Lookahead {
        private final LongSupplier countSupplier;

        @Getter
        private boolean reset;

        public long getCount() {
            return countSupplier.getAsLong();
        }

        public void reset() {
            reset=true;
        }
    }

    @Override
    public R scanner(BiConsumer<Lookahead,Scanner> scannerConsumer) {
        int index=state.getIndex();
        Charset charset=state.getCharset();
        try (InputStream inputStream=new GettableInputStream(gettable,index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             Scanner scanner=(charset==null?new Scanner(countingInputStream):new Scanner(countingInputStream,charset))) {

            SimpleLookahead lookahead=new SimpleLookahead(countingInputStream::getCount);
            scannerConsumer.accept(lookahead,scanner);
            if (!lookahead.isReset()) {
                state.setIndex(index+(int)countingInputStream.getCount());
            }
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return getThis();
    }

    @Override
    public C end() {
        return cursor;
    }
}
