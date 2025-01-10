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

package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.life.process.ProcessManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(staticName="of")
public class ProcessProcessManager implements ProcessManager<ProcessControl,Integer,IOException> {
    @Getter
    private final ProcessBuilder processBuilder;

    @Override
    public CompletableFuture<ProcessControl> create() throws IOException {
        CompletableFuture<ProcessControl> future=new CompletableFuture<>();
        try {
            Process process=processBuilder.start();
            ProcessControl processControl=ProcessControl.of(process);
            future.complete(processControl);
        } catch (IOException ex) {
            future.completeExceptionally(ex);
        }
        return future;
    }

    @Override
    public void close() throws IOException {  //TO-DO: Further consider and evaluate the need and sanity of a checked exception -- in relation to actual implementations!
        //Ignore!
    }
}
