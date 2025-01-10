/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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
