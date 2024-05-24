package com.yelstream.topp.execution.inject.spi;

import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.CDI;
import lombok.experimental.UtilityClass;

/**
 * Addresses instances of {@link BeanManager}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class BeanManagers {
    public static BeanManager getBeanManager(CDI<Object> cdi) {
        return cdi.getBeanManager();
    }

    public static BeanManager getBeanManager() {
        return getBeanManager(CDIs.getCDI());
    }
}
