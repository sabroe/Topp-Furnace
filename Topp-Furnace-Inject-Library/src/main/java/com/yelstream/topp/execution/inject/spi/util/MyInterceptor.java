/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
