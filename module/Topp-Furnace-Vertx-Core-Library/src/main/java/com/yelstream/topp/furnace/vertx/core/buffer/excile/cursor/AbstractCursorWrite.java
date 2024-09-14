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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@AllArgsConstructor
public abstract class AbstractCursorWrite<C extends AbstractCursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> implements CursorWrite<C,R,W> {
    /**
     *
     */
    private final C cursor;

    /**
     *
     */
    protected final Puttable puttable;

    /**
     *
     */
    protected final CursorState state;

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
        int index=state.getIndex();
        Charset charset=state.getCharset();
        StringBuilder sb=new StringBuilder();
        consumer.accept(sb);
        String builtString=sb.toString();
        byte[] bytes=builtString.getBytes(charset==null?StandardCharsets.UTF_8:charset);
        puttable.put(index,bytes);
        state.setIndex(index+bytes.length);
        return getThis();
    }

    @Override
    public W dataOutput(ConsumerWithException<DataOutput,IOException> consumer) {
        int index=state.getIndex();
        try (OutputStream outputStream=new PuttableOutputStream(puttable,index);
             CountingOutputStream countingOutputStream=new CountingOutputStream(outputStream);
             DataOutputStream dataOutputStream=new DataOutputStream(countingOutputStream)) {
            consumer.accept(dataOutputStream);
            state.setIndex(index+(int)countingOutputStream.getCount());
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
