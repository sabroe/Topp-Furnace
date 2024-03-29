package com.yelstream.topp.execution.inject.util;

import com.yelstream.topp.execution.tool.InvocationCountTracker;
import com.yelstream.topp.execution.inject.se.SeContainers;
import com.yelstream.topp.execution.inject.spi.CDIs;
import com.yelstream.topp.execution.inject.util.sniper.ConcurrencySniperInterceptor;
import com.yelstream.topp.execution.inject.util.sniper.ManagedBean1;
import com.yelstream.topp.execution.inject.util.sniper.ManagedBean2;
import jakarta.enterprise.inject.se.SeContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConcurrencySniperTest {

    static SeContainer container;

    @BeforeAll
    static void initialize() {
        System.setProperty("org.jboss.logging.provider","slf4j");
        System.setProperty("org.slf4j.simpleLogger.log.org.jboss.weld","info");
        //System.setProperty("org.slf4j.simpleLogger.log.org.jboss.weld","debug");
        System.setProperty("org.slf4j.simpleLogger.log.com.yelstream.topp.execution.inject.util","debug");
        container=SeContainers.createSeContainer();
    }

    @AfterAll
    static void destroy() {
        container.close();
    }

    @Test
    void interceptorOnBean() {
        ManagedBean1 bean1=CDIs.get(ManagedBean1.class);
        bean1.operation1();
        bean1.operation2();

        ConcurrencySniperInterceptor interceptor=ConcurrencySniperInterceptor.lookupInterceptor().byObject(bean1);
        InvocationCountTracker.Reader reader=interceptor.readInvocationCount();
        Assertions.assertEquals(2,reader.accumulated());
        Assertions.assertEquals(0,reader.active());
        Assertions.assertEquals(1,reader.maximumActive());
    }

    @Test
    void interceptorOnBeanWithInjector() {
        ManagedBean2 bean2=CDIs.get(ManagedBean2.class);
        bean2.operationA();
        bean2.operationB();
        bean2.operationB();

        Assertions.assertNotNull(bean2.getManagedBean1());  //Note: This triggers the 4. invocation.

        ConcurrencySniperInterceptor interceptor=ConcurrencySniperInterceptor.lookupInterceptor().byObject(bean2);
        InvocationCountTracker.Reader reader=interceptor.readInvocationCount();
        Assertions.assertEquals(4,reader.accumulated());
        Assertions.assertEquals(0,reader.active());
        Assertions.assertEquals(1,reader.maximumActive());
    }
}
