package com.yelstream.topp.furnace.manage.impl;

import com.yelstream.topp.furnace.manage.op.Destroyable;
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
