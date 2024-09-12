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

package com.yelstream.topp.furnace.vertx.core.buffer;

import com.google.common.io.CountingInputStream;
import com.google.common.io.CountingOutputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.ByteGettableInputStream;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.BytePuttableOutputStream;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import io.vertx.core.buffer.Buffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
@Getter
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder")
public class BufferCursor {
    /**
     * Vert.x buffer.
     */
    private final Buffer buffer;  //TO-DO: Consider bufferReference=new AtomicReference<Buffer>(buffer)! For expansion, possibly slicing!

    /**
     * Character set to use, if doing textual parsing.
     */
    @lombok.Builder.Default
    private final Charset charset=StandardCharsets.UTF_8;

    /**
     * Current index into the Vert.x buffer.
     */
    private int index;

//TO-DO: Supplier<Locale>, for Scanner!
//TO-DO: Supplier<ByteOrder>, for ByteBuffer!

    // --- Data Manipulation Methods ---

    // Write a string to the buffer at the current index
    public void writeString(String data) {
        byte[] bytes = data.getBytes(charset);
        buffer.setBytes(index, bytes);
        index += bytes.length;
    }

    // Read a string from the buffer from the current index
    public String readString(int length) {
        String data = buffer.getString(index, index + length, charset.name());
        index += length;
        return data;
    }

    // --- Look-ahead Token Parsing ---

    // Look ahead to check if the next token matches and consume it if true
    public boolean matchAndConsume(String token) {
        int tokenLength = token.length();
        if (index + tokenLength <= buffer.length()) {
            String nextToken = buffer.getString(index, index + tokenLength, charset.name());
            if (token.equals(nextToken)) {
                index += tokenLength;  // Consume the token by moving the index forward
                return true;
            }
        }
        return false;
    }

    // --- Utility Methods ---

    // Check if at the end of the buffer
    public boolean isAtEnd() {
        return index >= buffer.length();
    }


    /**
     * Uses a StringBuilder to build a string by applying the provided Consumer function.
     * The content of the StringBuilder is then written back to the buffer, and the cursor index is updated.
     *
     * @param stringBuilderConsumer A Consumer function that receives the StringBuilder for building the string.
     * @return The BufferCursor instance, with the index updated to the end of the newly written content.
     */
    public BufferCursor stringBuilder(Consumer<StringBuilder> stringBuilderConsumer) {
        StringBuilder sb=new StringBuilder();
        stringBuilderConsumer.accept(sb);
        String builtString=sb.toString();
        byte[] bytes=builtString.getBytes(charset);
        buffer.setBytes(index,bytes);
        index+=bytes.length;
        return this;
    }

    /**
     * Uses a Scanner to parse the buffer contents as text starting from the current index,
     * applying the provided Consumer function to it. The Scanner will use the charset defined
     * for the BufferCursor.
     *
     * @param scannerConsumer A Consumer function that receives the Scanner for processing the buffer's text.
     * @return The BufferCursor instance, with the index updated to the end of the processed content.
     */
    public BufferCursor scanner(BiConsumer<Lookahead,Scanner> scannerConsumer) {
        try (InputStream inputStream=new ByteGettableInputStream(Buffers.createByteGettable(buffer),index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             Scanner scanner=new Scanner(countingInputStream,charset)) {

            SimpleLookahead lookahead=new SimpleLookahead(countingInputStream::getCount);
            scannerConsumer.accept(lookahead,scanner);
            if (!lookahead.isReset()) {
                index+=countingInputStream.getCount();
            }
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return this;
    }

    public interface Lookahead {
        long getCount();
        void reset();
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

    public BufferCursor dataInput(ConsumerWithException<DataInput,IOException> consumer) {
        try (InputStream inputStream=new ByteGettableInputStream(Buffers.createByteGettable(buffer),index);
             CountingInputStream countingInputStream=new CountingInputStream(inputStream);
             DataInputStream dataInputStream=new DataInputStream(countingInputStream)) {
            consumer.accept(dataInputStream);
             index+=countingInputStream.getCount();
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return this;
    }

    public BufferCursor dataOutput(ConsumerWithException<DataOutput,IOException> consumer) {
        try (OutputStream outputStream=new BytePuttableOutputStream(Buffers.createBytePuttable(buffer),index);
             CountingOutputStream countingOutputStream=new CountingOutputStream(outputStream);
             DataOutputStream dataOutputStream=new DataOutputStream(countingOutputStream)) {
            consumer.accept(dataOutputStream);
            index+=countingOutputStream.getCount();
        } catch (IOException ex) {
            throw new IllegalStateException((ex));
        }
        return this;
    }

    //TODO: InputStream getInputStream()
    //TODO: OutputStream getOutputStream()
    //TODO: Reader getReader()
    //TODO: Writer getWriter()
    //TODO: ByteCursor formatter(Consumer<Formatter> formatterConsumer)  ??
    //TODO: ByteCursor dataInput(Consumer<DataInput>)
    //TODO: ByteCursor dataOutput(Consumer<DataOutput>)

    //TODO: ByteBuffer!
    //TODO: CharBuffer!

    //TODO: Consumer -> ConsumerWithException<IOException>
}
