package com.yelstream.topp.furnace.manage;

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
