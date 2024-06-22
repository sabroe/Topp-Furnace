package com.yelstream.topp.execution.inject.se;

import jakarta.enterprise.inject.se.SeContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeContainersTest {
    @Test
    void createSeContainerTest() {
        try (SeContainer container=SeContainers.createSeContainer()) {
            Assertions.assertNotNull(container);
        }
    }
}
