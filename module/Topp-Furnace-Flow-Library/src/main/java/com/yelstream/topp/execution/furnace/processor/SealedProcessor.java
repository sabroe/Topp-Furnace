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

package com.yelstream.topp.execution.furnace.processor;

import com.yelstream.topp.standard.lang.Runnables;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.Flow;

/**
 * Reactive processor composed of a preconfigured subscriber and publisher and sealed off for introspection.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-13
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class SealedProcessor<C,T,R> implements Flow.Processor<T,R>, AutoCloseable {
    @lombok.Builder.Default
    private final C context=null;

    @lombok.Builder.Default
    private final Runnable close=null;

    private final Flow.Subscriber<T> subscriber;

    private final Flow.Publisher<R> publisher;

    private volatile boolean closed;

    @Override
    public void subscribe(Flow.Subscriber<? super R> subscriber) {
        publisher.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T item) {
        subscriber.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }

    @SuppressWarnings({"java:S2583","ConstantValue"})
    @Override
    public void close() {
        if (!closed) {
            if (close!=null) {
                close.run();
            }
            closed=true;
        }
    }

    @SuppressWarnings({"java:S1068","UnusedDeclaration"})
    public static class Builder<C,T,R> {
        private Runnable close=null;

        public SealedProcessor.Builder<C,T,R> close(Runnable close) {
            this.close=close;
            return this;
        }

        public SealedProcessor.Builder<C,T,R> close(AutoCloseable closeable) {
            this.close=Runnables.of(closeable);
            return this;
        }
    }

    public static <C,T,R> SealedProcessor<C,T,R> of(Flow.Subscriber<T> subscriber,
                                                    Flow.Publisher<R> publisher) {
        SealedProcessor.Builder<C,T,R> builder=SealedProcessor.builder();
        builder.subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C,T,R> SealedProcessor<C,T,R> of(C context,
                                                    Flow.Subscriber<T> subscriber,
                                                    Flow.Publisher<R> publisher) {
        SealedProcessor.Builder<C,T,R> builder=SealedProcessor.builder();
        builder.context(context).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C extends AutoCloseable,T,R> SealedProcessor<C,T,R> of(C context,
                                                                          Flow.Subscriber<T> subscriber,
                                                                          Flow.Publisher<R> publisher) {
        SealedProcessor.Builder<C,T,R> builder=SealedProcessor.builder();
        builder.context(context).close(context).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C,T,R> SealedProcessor<C,T,R> of(C context,
                                                    AutoCloseable close,
                                                    Flow.Subscriber<T> subscriber,
                                                    Flow.Publisher<R> publisher) {
        SealedProcessor.Builder<C,T,R> builder=SealedProcessor.builder();
        builder.context(context).close(close).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }
}
