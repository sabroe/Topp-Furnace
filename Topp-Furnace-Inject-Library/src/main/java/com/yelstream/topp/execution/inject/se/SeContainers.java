package com.yelstream.topp.execution.inject.se;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import lombok.experimental.UtilityClass;

/**
 * Addresses instances of {@link SeContainer}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class SeContainers {
    public static SeContainer createSeContainer() {
        SeContainerInitializer initializer=SeContainerInitializer.newInstance();
        return initializer.initialize();
    }
}
