/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
public interface CursorRead<C extends Cursor<C,R,W>, R extends CursorRead<C,R,W>, W extends CursorWrite<C,R,W>> {

    R inputStream(ConsumerWithException<InputStream,IOException> consumer);

    R reader(ConsumerWithException<Reader,IOException> consumer);

    R dataInput(ConsumerWithException<DataInput,IOException> consumer);

    R byteBuffer(Consumer<ByteBuffer> consumer);

    R charBuffer(Consumer<CharBuffer> consumer);

//    R string(int length, Consumer<String> consumer);

//    R matchAndConsume(String token, BooleanConsumer consumer);
//    R matchAndConsume(String token, Consumer<Boolean> consumer);



    interface Lookahead {
        long getCount();
        void reset();
    }

    /**
     * Uses a Scanner to parse the buffer contents as text starting from the current index,
     * applying the provided Consumer function to it. The Scanner will use the charset defined
     * for the BufferCursor.
     *
     * @param scannerConsumer A Consumer function that receives the Scanner for processing the buffer's text.
     * @return The BufferCursor instance, with the index updated to the end of the processed content.
     */
    R scanner(BiConsumer<Lookahead, Scanner> scannerConsumer);

    C end();

/*
    public String readString(int length) {
        String data = buffer.getString(index, index + length, charset.name());
        index += length;
        return data;
    }
*/

/*
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
*/
}
