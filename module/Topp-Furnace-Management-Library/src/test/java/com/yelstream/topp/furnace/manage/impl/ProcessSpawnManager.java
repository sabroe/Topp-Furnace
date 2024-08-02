package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.SpawnManager;
import com.yelstream.topp.furnace.manage.Stoppable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ProcessSpawnManager implements SpawnManager<ProcessSpawnManager.ProcessManager, Integer, IOException> {

    private final ProcessBuilder processBuilder;

    private ProcessManager processManager;

    public ProcessSpawnManager(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    @Override
    public CompletableFuture<ProcessManager> start() throws IOException {
        CompletableFuture<ProcessManager> future = new CompletableFuture<>();
        try {
            Process process = processBuilder.start();
            processManager = new ProcessManager(process);
            future.complete(processManager);
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public void close() throws IOException {
        if (processManager != null) {
            try {
                processManager.stop().join();
            } catch (Exception ex) {
                throw new IOException("Failed to stop the process", ex);
            }
        }
    }

    public static class ProcessManager implements Stoppable<Integer, IOException> {

        private final Process process;

        public ProcessManager(Process process) {
            this.process = process;
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

}
