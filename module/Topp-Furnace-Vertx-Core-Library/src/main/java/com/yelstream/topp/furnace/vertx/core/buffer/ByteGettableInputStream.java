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

import java.io.IOException;
import java.io.InputStream;

/**
 * Stream reading from an exposed byte buffer.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-11
 */
public class ByteGettableInputStream extends InputStream {

    private final ByteGettable gettable;

    private int currentPosition = 0;

    private int markPosition = -1;

    public ByteGettableInputStream(ByteGettable gettable,
                                   int currentPosition) {
        this.gettable=gettable;
        this.currentPosition=currentPosition;
    }

    public ByteGettableInputStream(ByteGettable gettable) {
        this(gettable,0);
    }

    @Override
    public int read() throws IOException {
        if (currentPosition >= gettable.length()) {
            return -1;
        }
        byte value = gettable.get(currentPosition);
        currentPosition++;
        return value & 0xFF; // Convert byte to int
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (currentPosition >= gettable.length()) {
            return -1;
        }
        int remaining = gettable.length() - currentPosition;
        int toRead = Math.min(len, remaining);
        gettable.get(currentPosition, b, off, toRead);
        currentPosition += toRead;
        return toRead;
    }

    @Override
    public void mark(int readlimit) {
        markPosition = currentPosition;
    }

    @Override
    public void reset() throws IOException {
        if (markPosition < 0) {
            throw new IOException("Mark not set");
        }
        currentPosition = markPosition;
    }

    @Override
    public boolean markSupported() {
        return true;
    }
}
