package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.LifecycleManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ProcessLifecycleManager implements LifecycleManager<Process, Integer, IOException> {

    private final ProcessBuilder processBuilder;
    private Process process;

    public ProcessLifecycleManager(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    @Override
    public CompletableFuture<Process> start() throws IOException {
        CompletableFuture<Process> future = new CompletableFuture<>();
        try {
            process = processBuilder.start();
            future.complete(process);
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public CompletableFuture<Integer> stop() throws IOException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        if (process != null && process.isAlive()) {
            process.destroy();
            try {
                int exitCode = process.waitFor();
                future.complete(exitCode);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        } else {
            future.complete(0); // Process already stopped or not started
        }
        return future;
    }
}
