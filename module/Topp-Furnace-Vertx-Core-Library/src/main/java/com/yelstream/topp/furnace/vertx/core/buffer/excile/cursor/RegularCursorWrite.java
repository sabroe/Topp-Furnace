package com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;

import com.google.common.io.CountingOutputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.PuttableOutputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import lombok.AllArgsConstructor;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.function.Consumer;

@AllArgsConstructor
public abstract class RegularCursorWrite<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorWrite<C,R,W> {
//TO-DO: Consider making this abstract!
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    private final Puttable puttable;

    /**
     *
     */
    protected abstract W getThis();

    @Override
    public W outputStream(ConsumerWithException<OutputStream,IOException> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public W writer(ConsumerWithException<Writer,IOException> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public W printStream(Consumer<PrintStream> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public W byteBuffer(Consumer<ByteBuffer> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public W charBuffer(Consumer<CharBuffer> consumer) {
        //TO-DO: Fix!
        return getThis();
    }

    @Override
    public W stringBuilder(Consumer<StringBuilder> consumer) {
        StringBuilder sb=new StringBuilder();
        consumer.accept(sb);
        String builtString=sb.toString();
        byte[] bytes=builtString.getBytes(cursor.charset);
        puttable.put(cursor.index,bytes);
        cursor.index+=bytes.length;
        return getThis();
    }

    @Override
    public W dataOutput(ConsumerWithException<DataOutput,IOException> consumer) {
        try (OutputStream outputStream=new PuttableOutputStream(puttable,cursor.index);
             CountingOutputStream countingOutputStream=new CountingOutputStream(outputStream);
             DataOutputStream dataOutputStream=new DataOutputStream(countingOutputStream)) {
            consumer.accept(dataOutputStream);
            cursor.index+=countingOutputStream.getCount();
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
