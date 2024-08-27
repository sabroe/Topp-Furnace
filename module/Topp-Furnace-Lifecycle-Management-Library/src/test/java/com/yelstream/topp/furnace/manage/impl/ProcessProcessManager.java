package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.ProcessManager;
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
