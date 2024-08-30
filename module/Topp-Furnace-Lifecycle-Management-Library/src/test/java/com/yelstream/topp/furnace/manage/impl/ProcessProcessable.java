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

import com.yelstream.topp.furnace.life.manage.AbstractProcessable;
import com.yelstream.topp.furnace.life.manage.ProcessManager;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;

public class ProcessProcessable extends AbstractProcessable<ProcessControl,Integer,IOException> {

    @Getter
    private final ProcessBuilder processBuilder;

    @lombok.Builder(builderClassName="Builder",access= AccessLevel.PRIVATE)
    private ProcessProcessable(ProcessBuilder processBuilder,
                               ProcessManager<ProcessControl,Integer,IOException> manager) {
        super(manager);
        this.processBuilder=processBuilder;
    }

    public static ProcessProcessable of() {
        return builder().build();
    }

    public static ProcessProcessable of(ProcessBuilder processBuilder) {
        return builder().processBuilder(processBuilder).build();
    }

    private static class Builder {
        public ProcessProcessable build() {
            if (processBuilder==null) {
                processBuilder=new ProcessBuilder();
            }
            if (manager==null) {
                manager=ProcessProcessManager.of(processBuilder);
            }
            return new ProcessProcessable(processBuilder,manager);
        }
    }
}
