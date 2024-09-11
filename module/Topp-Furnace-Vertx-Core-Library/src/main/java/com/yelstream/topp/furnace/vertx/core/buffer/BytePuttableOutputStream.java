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

import io.vertx.core.buffer.Buffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Stream writing to an exposed byte buffer.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-11
 */
public class BytePuttableOutputStream extends OutputStream {

    private final BytePuttable puttable;

    private int currentPosition;

    private int markPosition = -1;

    public BytePuttableOutputStream(BytePuttable puttable, int startPosition) {
        this.puttable=puttable;
        this.currentPosition=startPosition;
    }

    public BytePuttableOutputStream(BytePuttable puttable) {
        this(puttable,0);
    }

    @Override
    public void write(int b) throws IOException {
        if (currentPosition >= puttable.length()) {
            puttable.put(currentPosition,(byte)b);  //TO-DO: Consider this; allow auto-expansion?
        } else {
            puttable.put(currentPosition,(byte)b);
        }
        currentPosition++;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (currentPosition+len>puttable.length()) {
            puttable.put(currentPosition, b, off, len);  //TO-DO: Consider this; allow auto-expansion?
        } else {
            puttable.put(currentPosition, b, off, len);
        }
        currentPosition+=len;
    }

    //    @Override
    public void mark(int readlimit) {
        markPosition = currentPosition;
    }

    //    @Override
    public void reset() throws IOException {
        if (markPosition < 0) {
            throw new IOException("Mark not set");
        }
        currentPosition = markPosition;
    }

    //    @Override
    public boolean markSupported() {
        return true;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
