/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.execution.inject.util.sniper;

import com.yelstream.topp.execution.cdi.interceptor.InterceptorRegister;
import com.yelstream.topp.execution.tool.InvocationCountTracker;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

/**
 * Implements that there is to be kept a watchful eye on thread-related activities,
 * monitors the technical aspect of concurrency control.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-01
 */
@Slf4j
@Dependent
@Interceptor
@ConcurrencySniper
@NoArgsConstructor
public class ConcurrencySniperInterceptor {

    private static final InterceptorRegister<ConcurrencySniper,ConcurrencySniperInterceptor> interceptorRegister=
        InterceptorRegister.of(ConcurrencySniper.class,ConcurrencySniper::id);

    public static InterceptorRegister<ConcurrencySniper,ConcurrencySniperInterceptor>.Lookup lookupInterceptor() {
        return interceptorRegister.getLookup();
    }

    private final InvocationCountTracker invocationCountTracker=InvocationCountTracker.of();

    public InvocationCountTracker.Reader readInvocationCount() {
        return invocationCountTracker.getReader();
    }

    private ConcurrencySniper annotation;

    @PostConstruct
    private void init(InvocationContext context) {
        log.debug("Concurrency sniper interceptor is initializing; target is '{}'.",context.getTarget());
        annotation=interceptorRegister.getManager().add(context,this);
    }

    @PreDestroy
    private void destroy(InvocationContext context) {
        log.debug("Concurrency sniper interceptor is destroying; target is '{}'.",context.getTarget());
        interceptorRegister.getManager().remove(annotation);
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        try (InvocationCountTracker.Invocation invocation=invocationCountTracker.count()) {
            int count=invocation.getActive();
            log.debug("Concurrency sniper interceptor at method-begin; target is '{}', method is '{}'; invocation count is {}.",context.getTarget(),context.getMethod(),count);

            Level level=null;
            if (count>=annotation.errorAt()) {
                level=Level.ERROR;
            } else {
                if (count>=annotation.warningAt()) {
                    level=Level.WARN;
                }
            }
            if (level!=null) {
                log.atLevel(level).log("Concurrency sniper interceptor method-invocation count is critical; target is '{}', method is '{}', invocation count is {}.",context.getTarget(),context.getMethod(),count);
            }

            return context.proceed();
        } finally {
            log.debug("Concurrency sniper interceptor at method-end; target is '{}', method is '{}'.",context.getTarget(),context.getMethod());
        }
    }
}
