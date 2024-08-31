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

package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.life.process.op.Destroyable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(staticName="of")
public class ProcessControl implements Destroyable<Integer,IOException> {

    @Getter
    private final Process process;

    @Override
    public CompletableFuture<Integer> destroy() throws IOException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        if (process==null || !process.isAlive()) {
            future.complete(null);
        } else {
            process.destroy();
            process.onExit().whenComplete((process,ex) -> {
                if (ex!=null) {
                    future.completeExceptionally(ex);
                } else {
                    future.complete(process.exitValue());
                }
            });
        }
        return future;
    }
}
