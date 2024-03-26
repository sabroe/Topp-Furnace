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
