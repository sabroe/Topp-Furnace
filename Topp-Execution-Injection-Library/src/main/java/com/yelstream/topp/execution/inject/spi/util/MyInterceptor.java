package com.yelstream.topp.execution.inject.spi.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

@Dependent
public class MyInterceptor {

    @Inject
    private MyApplicationScopedBean myApplicationScopedBean;

    @PostConstruct
    public void init() {
        // Initialization logic
    }

    @PreDestroy
    public void destroy() {
        // Destruction logic
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        // Access the counter from the associated @ApplicationScoped bean
        int counterValue = myApplicationScopedBean.getCounter();
        System.out.println("Interceptor received counter: " + counterValue);

        // Perform the interception logic
        Object result = context.proceed();

        // Access the counter again after the method invocation
        counterValue = myApplicationScopedBean.getCounter();
        System.out.println("Interceptor received counter after invocation: " + counterValue);

        return result;
    }
}
