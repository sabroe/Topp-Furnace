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

package com.yelstream.topp.furnace.vertx.core.buffer.cursor;

import com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor.AbstractCursor;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Gettable;
import com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer.Puttable;
import com.yelstream.topp.furnace.vertx.core.buffer.io.BufferGettable;
import com.yelstream.topp.furnace.vertx.core.buffer.io.BufferPuttable;
import io.vertx.core.buffer.Buffer;
import lombok.Getter;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
@Getter
//@AllArgsConstructor(staticName="of")
public class BufferCursor extends AbstractCursor<BufferCursor, BufferCursorRead, BufferCursorWrite> {
    /**
     * Vert.x buffer.
     */
    private final Buffer buffer;  //TO-DO: Consider bufferReference=new AtomicReference<Buffer>(buffer)! For expansion, possibly slicing!

    public BufferCursor(Buffer buffer) {
        super(()->new BufferGettable(buffer),()->new BufferPuttable(buffer));
        this.buffer=buffer;
    }

    @Override
    public BufferCursorRead read() {
        Gettable gettable=state.getGettableSupplier().get();
        return new BufferCursorRead(this,buffer,gettable,state);
    }

    @Override
    public BufferCursorWrite write() {
        Puttable puttable=state.getPuttableSupplier().get();
        return new BufferCursorWrite(this,buffer,puttable,state);
    }

    @Override
    public BufferCursor end() {
        return this;
    }

/*
    public void writeString(String data) {
        byte[] bytes = data.getBytes(charset);
        buffer.setBytes(index, bytes);
        index += bytes.length;
    }
*/

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

/*
    public boolean isAtEnd() {
        return index >= buffer.length();
    }
*/
}
