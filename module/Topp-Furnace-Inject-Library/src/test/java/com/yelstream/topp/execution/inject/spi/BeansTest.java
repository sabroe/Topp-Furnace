package com.yelstream.topp.execution.inject.spi;

import com.yelstream.topp.execution.inject.se.SeContainers;
import jakarta.enterprise.inject.se.SeContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BeansTest {
    static SeContainer container;

    @BeforeAll
    static void initialize() {
        System.setProperty("org.jboss.logging.provider","slf4j");
        System.setProperty("org.slf4j.simpleLogger.log.org.jboss.weld","info");
        container=SeContainers.createSeContainer();
    }

    @AfterAll
    static void destroy() {
        container.close();
    }

    @Test
    void logBeansTest() {
        Beans.logBeans();
        Assertions.assertTrue(true);
    }
}
