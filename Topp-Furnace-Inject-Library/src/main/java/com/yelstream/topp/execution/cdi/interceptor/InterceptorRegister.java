package com.yelstream.topp.execution.cdi.interceptor;

import jakarta.interceptor.InvocationContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Register of interceptors.
 *
 * @param <A> Type of annotation.
 * @param <I> Type of interceptor.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-05
 */
@Slf4j
@RequiredArgsConstructor(staticName="of")
public class InterceptorRegister<A extends Annotation,I> {
    private final Map<String,I> interceptorById=new ConcurrentHashMap<>();

    private final Class<A> annotationClass;

    private final Function<A,String> annotationToId;

    public final class Manager {
        public void add(A annotation,
                        I interceptor) {
            String id=annotationToId.apply(annotation);
            if (id!=null && !id.isEmpty()) {
                interceptorById.put(id,interceptor);
            }
        }

        public A add(InvocationContext invocationContext,
                     I interceptor) {
            A annotation=invocationContext.getTarget().getClass().getAnnotation(annotationClass);
            add(annotation,interceptor);
            return annotation;
        }

        public void remove(A annotation) {
            String id=annotationToId.apply(annotation);
            if (id!=null && !id.isEmpty()) {
                interceptorById.remove(id);
            }
        }

        public A remove(InvocationContext invocationContext) {
            A annotation=invocationContext.getTarget().getClass().getAnnotation(annotationClass);
            remove(annotation);
            return annotation;
        }
    }

    @Getter
    private final Manager manager=new Manager();

    public final class Lookup {
        public I byId(String id) {
            return interceptorById.get(id);
        }

        public I byAnnotation(A annotation) {
            String id=annotationToId.apply(annotation);
            return byId(id);
        }

        public I byObject(Object object) {
            A annotation=object.getClass().getAnnotation(annotationClass);
            return byAnnotation(annotation);
        }
    }

    @Getter
    private final Lookup lookup=new Lookup();
}
