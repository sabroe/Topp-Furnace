package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.google.common.io.CountingInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.Buffers;
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
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongSupplier;

@AllArgsConstructor
public abstract class RegularCursorRead<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorRead<C,R,W> {
//TO-DO: Consider making this abstract!
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    private final Gettable gettable;

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
        try (InputStream inputStream=new GettableInputStream(gettable,cursor.index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             DataInputStream dataInputStream=new DataInputStream(countingInputStream)) {
            consumer.accept(dataInputStream);
            cursor.index+=countingInputStream.getCount();
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
        try (InputStream inputStream=new GettableInputStream(gettable,cursor.index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             Scanner scanner=new Scanner(countingInputStream,cursor.charset)) {

            SimpleLookahead lookahead=new SimpleLookahead(countingInputStream::getCount);
            scannerConsumer.accept(lookahead,scanner);
            if (!lookahead.isReset()) {
                cursor.index+=countingInputStream.getCount();
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
