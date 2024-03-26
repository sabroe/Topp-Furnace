package com.yelstream.topp.execution.inject.spi;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessBean;

public class MyInterceptorExtension implements Extension {

    /*
     * META-INF/services/jakarta.enterprise.inject.spi.Extension
     *    com.example.MyInterceptorExtension
     */

/*
    <T> void processBeans(@Observes ProcessBean<T> event) {
        Annotated annotated = event.getAnnotated();
        if (annotated.isAnnotationPresent(ApplicationScoped.class)) {
            // Add your interceptor to the bean
            event.setBean(processBeanAttributes(event.getBeanAttributes(), event.getAnnotated()));
        }
    }

    private <T> BeanAttributes<T> processBeanAttributes(BeanAttributes<T> attributes, Annotated annotated) {
        // Add your interceptor to the bean attributes
        return new MyInterceptedBeanAttributes<>(attributes);
    }
*/



    <T> void processBeans(@Observes ProcessBean<T> event, BeanManager beanManager) {
        Annotated annotated = event.getAnnotated();
/*
        if (annotated.isAnnotationPresent(ApplicationScoped.class)) {
            // Create a new Bean with the interceptor
            Bean<T> originalBean = event.getBean();
            BeanConfigurator<T> configurator = BeanConfigurator.create(beanManager, originalBean.getBeanClass());
            configurator.intercept(MyInterceptor.class);
            Bean<T> modifiedBean = configurator.build();

            // Replace the original Bean with the modified Bean
            event.setBean(modifiedBean);
        }
*/
    }
}