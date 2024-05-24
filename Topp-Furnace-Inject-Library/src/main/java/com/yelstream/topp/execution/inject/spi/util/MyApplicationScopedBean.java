package com.yelstream.topp.execution.inject.spi.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.CDI;

@ApplicationScoped
public class MyApplicationScopedBean {

    private int counter = 0;

    public void sayHello() {
        // Fire a CDI event to communicate with the interceptor
        CDI.current().getBeanManager().getEvent().select(MyApplicationScopedBean.class).fire(this);

        System.out.println("Hello from MyApplicationScopedBean! Counter: " + counter);
    }

    // CDI observer method to handle the event fired by the bean
    public void handleEvent(@Observes MyApplicationScopedBean event) {
        // Access the counter from the bean
        int counterValue = event.getCounter();
        System.out.println("Interceptor received counter: " + counterValue);
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        counter++;
    }
}
