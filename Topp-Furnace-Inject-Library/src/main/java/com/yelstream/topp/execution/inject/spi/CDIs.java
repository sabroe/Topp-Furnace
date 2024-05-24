package com.yelstream.topp.execution.inject.spi;

import jakarta.enterprise.inject.spi.CDI;
import lombok.experimental.UtilityClass;

/**
 * Addresses instances of {@link CDI}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class CDIs {
    public static CDI<Object> getCDI() {
        return CDI.current();
    }

    public static <X> X get(CDI<Object> cdi,
                            Class<X> clazz) {
        return cdi.select(clazz).get();
    }

    public static <X> X get(Class<X> clazz) {
        return get(getCDI(),clazz);
    }
}
