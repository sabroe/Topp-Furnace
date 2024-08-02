package com.yelstream.topp.furnace.manage;

import com.yelstream.topp.furnace.manage.impl.ProcessSpawnManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ProcessSpawnManagerTest {

    @Test
    void main() {

        try (ProcessSpawnManager processManager = new ProcessSpawnManager(new ProcessBuilder("notepad"))) {
            ProcessSpawnManager.ProcessManager process = processManager.start().join();
            // Do something with the process
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
