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
