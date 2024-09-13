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
