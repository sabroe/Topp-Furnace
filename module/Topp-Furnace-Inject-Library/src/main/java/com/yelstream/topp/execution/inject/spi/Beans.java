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

package com.yelstream.topp.execution.inject.spi;

import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;

/**
 * Addresses instances of {@link Bean}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class Beans {
    @SuppressWarnings("java:S1452")
    public static Set<Bean<?>> getBeans(BeanManager beanManager) {
        return beanManager.getBeans(Object.class);
    }

    @SuppressWarnings("java:S1452")
    public static Set<Bean<?>> getBeans() {
        return getBeans(BeanManagers.getBeanManager());
    }

    public static void logBeans(Collection<Bean<?>> beans) {
        if (beans != null) {
            beans.forEach(bean -> {
                System.out.println("Bean Type: " + bean.getBeanClass().getName());
                System.out.println("Bean Qualifiers: " + bean.getQualifiers());
                System.out.println("Bean Name: " + bean.getName());
                System.out.println("Bean injection points: " + bean.getInjectionPoints());
                System.out.println("Bean qualifiers: " + bean.getQualifiers());
                System.out.println("--------");
            });
        }
    }

    public static void logBeans() {
        logBeans(getBeans());
    }
}
