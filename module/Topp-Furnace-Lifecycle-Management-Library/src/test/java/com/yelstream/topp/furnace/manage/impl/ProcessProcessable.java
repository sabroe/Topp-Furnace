package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.AbstractProcessable;
import com.yelstream.topp.furnace.manage.ProcessManager;
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
