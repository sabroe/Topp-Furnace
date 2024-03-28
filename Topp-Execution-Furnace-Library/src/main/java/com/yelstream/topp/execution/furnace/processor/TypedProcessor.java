package com.yelstream.topp.execution.furnace.processor;

import com.yelstream.topp.execution.lang.Runnables;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Flow;

/**
 * Reactive processor composed of a preconfigured subscriber and publisher and open of for introspection.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-13
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class TypedProcessor<C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> implements Flow.Processor<T,R>, AutoCloseable {
    @Getter  //Open for introspection
    @lombok.Builder.Default
    private final C context=null;

    @lombok.Builder.Default
    private final Runnable close=null;

    @Getter  //Open for introspection
    private final S subscriber;

    @Getter  //Open for introspection
    private final P publisher;

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

    @Override
    public void close() {
        if (close!=null) {
            close.run();
        }
    }

    public static class Builder<C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> {
        private Runnable close=null;

        public Builder<C,T,R,S,P> close(Runnable close) {
            this.close=close;
            return this;
        }

        public Builder<C,T,R,S,P> close(AutoCloseable closeable) {
            this.close=Runnables.createRunnable(closeable);
            return this;
        }
    }

    public static <C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> of(S subscriber,
                                                                                                                P publisher) {
        TypedProcessor.Builder<C,T,R,S,P> builder=TypedProcessor.builder();
        builder.subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> of(C context,
                                                                                                                S subscriber,
                                                                                                                P publisher) {
        TypedProcessor.Builder<C,T,R,S,P> builder=TypedProcessor.builder();
        builder.context(context).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C extends AutoCloseable,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> of(C context,
                                                                                                                                      S subscriber,
                                                                                                                                      P publisher) {
        TypedProcessor.Builder<C,T,R,S,P> builder=TypedProcessor.builder();
        builder.context(context).close(context).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> of(C context,
                                                                                                                AutoCloseable close,
                                                                                                                S subscriber,
                                                                                                                P publisher) {
        TypedProcessor.Builder<C,T,R,S,P> builder=TypedProcessor.builder();
        builder.context(context).close(close).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }
}
