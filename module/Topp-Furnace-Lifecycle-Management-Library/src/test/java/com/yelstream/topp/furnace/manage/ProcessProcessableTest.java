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

package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.life.process.ProcessManager;
import com.yelstream.topp.furnace.manage.impl.ProcessControl;
import com.yelstream.topp.furnace.manage.impl.ProcessProcessable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

class ProcessProcessableTest {

    @Test
    void main() throws Exception {

        try (ProcessProcessable processProcessable=ProcessProcessable.of(new ProcessBuilder("notepad"))) {
            ProcessManager<ProcessControl,Integer,IOException> processManager=processProcessable.getManager();
            CompletableFuture<ProcessControl> x=processManager.create();
            // Do something with the process

            ProcessControl control=x.get();
            Thread.sleep(Duration.ofSeconds(2));
            control.destroy().get();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
