package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Slide;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Space;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public final class BufferCursorGet {
    /**
     *
     */
    private final BufferCursorRead cursorRead;

    /**
     *
     */
    private final Buffer buffer;

    /**
     * Buffer access.
     */
    private final Space space;

    /**
     * Settings for indexing into buffer.
     */
    private final Slide slide;

    public BufferCursorRead end() {
        return cursorRead;
    }

    private interface Operation {
        void apply(Buffer buffer, int index, IntConsumer indexConsumer);
    }

    private BufferCursorGet op(Operation operation) {
        Buffer buffer=this.buffer;
        int index=slide.getIndex();
        AtomicInteger count=new AtomicInteger();
        IntConsumer countConsumer=c->count.set(c);
        operation.apply(buffer,index,countConsumer);
        slide.setIndex(index+count.get());
        return this;
    }

    public BufferCursorGet getByte(Consumer<Byte> consumer) {  //TO-DO: Consumer<Byte> -> ByteConsumer!
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Byte.BYTES);
            consumer.accept(buffer.getByte(pos));
        });
    }

    public BufferCursorGet getUnsignedByte(Consumer<Short> consumer) {  //TO-DO: Consumer<Short> -> ShortConsumer!
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            consumer.accept(buffer.getUnsignedByte(pos));
        });
    }

    public BufferCursorGet getInt(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            consumer.accept(buffer.getInt(pos));
        });
    }

    public BufferCursorGet getIntLE(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            consumer.accept(buffer.getIntLE(pos));
        });
    }

    public BufferCursorGet getUnsignedInt(LongConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            consumer.accept(buffer.getUnsignedInt(pos));
        });
    }

    public BufferCursorGet getUnsignedIntLE(LongConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            consumer.accept(buffer.getUnsignedIntLE(pos));
        });
    }

    public BufferCursorGet getLong(LongConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            consumer.accept(buffer.getLong(pos));
        });
    }

    public BufferCursorGet getLongLE(LongConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Long.BYTES);
            consumer.accept(buffer.getLongLE(pos));
        });
    }

    public BufferCursorGet getDouble(DoubleConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Double.BYTES);
            consumer.accept(buffer.getDouble(pos));
        });
    }

    public BufferCursorGet getFloat(Consumer<Float> consumer) {  //TO-DO: Consumer<Float> -> FloatConsumer!
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Float.BYTES);
            consumer.accept(buffer.getFloat(pos));
        });
    }

    public BufferCursorGet getShort(Consumer<Short> consumer) {  //TO-DO: Consumer<Short> -> ShortConsumer!
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            consumer.accept(buffer.getShort(pos));
        });
    }

    public BufferCursorGet getShortLE(Consumer<Short> consumer) {  //TO-DO: Consumer<Short> -> ShortConsumer!
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Short.BYTES);
            consumer.accept(buffer.getShortLE(pos));
        });
    }

    public BufferCursorGet getUnsignedShort(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            consumer.accept(buffer.getUnsignedShort(pos));
        });
    }

    public BufferCursorGet getUnsignedShortLE(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(Integer.BYTES);
            consumer.accept(buffer.getUnsignedShortLE(pos));
        });
    }

    public BufferCursorGet getMedium(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            consumer.accept(buffer.getMedium(pos));
        });
    }

    public BufferCursorGet getMediumLE(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            consumer.accept(buffer.getMediumLE(pos));
        });
    }

    public BufferCursorGet getUnsignedMedium(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            consumer.accept(buffer.getUnsignedMedium(pos));
        });
    }

    public BufferCursorGet getUnsignedMediumLE(IntConsumer consumer) {
        return op((buffer,pos,countConsumer)->{
            countConsumer.accept(3);
            consumer.accept(buffer.getUnsignedMediumLE(pos));
        });
    }





    public String getString(int start,
                            int end,
                            String enc) {
        return null;
    }

    public String getString(int start,
                            int end) {
        return null;
    }
}