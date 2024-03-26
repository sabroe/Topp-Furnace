package com.yelstream.topp.execution.inject.spi.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.spi.Context;
import jakarta.enterprise.context.spi.Contextual;
import jakarta.enterprise.context.spi.CreationalContext;

import java.lang.annotation.Annotation;

public class CustomContext implements Context {

    @Override
    public <T> T get(Contextual<T> contextual) {
        // Implement the logic to get information associated with the contextual
        // For simplicity, we return null in this example
        return null;
    }

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        // Implement the logic to get information associated with the contextual
        // For simplicity, we return null in this example
        return null;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
