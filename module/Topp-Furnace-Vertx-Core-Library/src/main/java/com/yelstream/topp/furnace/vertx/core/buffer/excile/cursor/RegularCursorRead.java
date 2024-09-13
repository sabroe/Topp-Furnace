package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.google.common.io.CountingInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.Buffers;
import com.yelstream.topp.furnace.vertx.core.buffer.cursor.BufferCursor;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.GettableInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AllArgsConstructor;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public class RegularCursorRead<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorRead<C,R,W> {
    /**
     *
     */
    protected C cursor() {
        
    }

    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    private final Gettable gettable;

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
        return this;
    }

    @Override
    public C end() {
        return cursor;
    }
}
