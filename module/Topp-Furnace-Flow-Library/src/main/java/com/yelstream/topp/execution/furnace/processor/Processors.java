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

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

/**
 * Utilities addressing instances of {@link Flow.Processor}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-13
 */
@UtilityClass
public class Processors {


    public static <T,R> Flow.Processor<T,R> createProcessor(Flow.Subscriber<T> subscriber,
                                                            Flow.Publisher<R> publisher) {
        return SealedProcessor.of(subscriber,publisher);
    }

    public static <C,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> createTypedProcessor(C context,
                                                                                                                                  S subscriber,
                                                                                                                                  P publisher) {
        return TypedProcessor.of(context,subscriber,publisher);
    }

    public static <C extends AutoCloseable,T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<C,T,R,S,P> createProcessor(C context,
                                                                                                                                                   S subscriber,
                                                                                                                                                   P publisher) {
        TypedProcessor.Builder<C,T,R,S,P> builder=TypedProcessor.builder();
        builder.context(context).close(context).subscriber(subscriber).publisher(publisher);
        return builder.build();
    }

    public static <R,P extends Flow.Publisher<R>> TypedProcessor<Multi<R>,R,R,BroadcastProcessor<R>,P> createProcessor(P publisher) {
        BroadcastProcessor<R> subscriber=BroadcastProcessor.create();
        Multi<R> multi=Multi
            .createFrom().publisher(publisher)
            .subscribe().withSubscriber(subscriber)
            .toHotStream();
        return TypedProcessor.of(multi,subscriber,publisher);
    }


    public static <R> TypedProcessor<Multi<R>,R,R,UnicastProcessor<R>,Flow.Publisher<R>> createProcessor() {
        UnicastProcessor<R> processor=UnicastProcessor.create();
        BroadcastProcessor<R> broadcastProcessor=BroadcastProcessor.create();
        Multi<R> multi=processor
            .onItem().invoke(item -> System.out.println("A>> " + item))
//            .toHotStream()
            .subscribe().withSubscriber(broadcastProcessor)
;//            .toHotStream();
        return TypedProcessor.of(multi,processor,broadcastProcessor);
    }

/*
    public static <R,S extends Flow.Subscriber<R>> TypedProcessor<R,R,P,BroadcastProcessor<R>,S,Multi<R>> createProcessor(S subscriber) {
        BroadcastProcessor<R> processor=BroadcastProcessor.create();
        Multi<R> multi=Multi
                .createFrom().publisher(publisher)
                .subscribe().withSubscriber(processor)
                .toHotStream();
        return TypedProcessor.of(multi,publisher,processor);
    }
*/

    public static void main(String[] args) throws InterruptedException {
        Flow.Processor<String,String> processor=createProcessor();
//        TypedProcessor<String,String,UnicastProcessor<String>,Flow.Subscriber<String>,Multi<String>> processor=createProcessor();

//        Cancellable cancellable1=processor.subscribe().with(item -> System.out.println(">> " + item));
//        Cancellable cancellable2=multi2.subscribe().with(item -> System.out.println(">> " + item));

// Create a source of items that does not follow the request protocol
        Thread t=new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                processor.onNext(Integer.toString(i));
            }
            processor.onComplete();
        });
        t.start();

        t.join();
    }

/*
    public static <T,R,S extends Flow.Subscriber<T>,P extends Flow.Publisher<R>> TypedProcessor<T,R,S,P> createProcessor(Flow.Processor<R> processor,
                                                                                                                         Flow.Subscriber<T> subscriber) {

        UnicastProcessor<String> processor = UnicastProcessor.create();
        Multi<String> multi = processor
                .onItem().transform(String::toUpperCase)
                .onFailure().recoverWithItem("d'oh")
                .onItem().invoke(item -> System.out.println("A>> " + item))
                ;//.toHotStream();
        BroadcastProcessor<String> processor2 = BroadcastProcessor.create();
        Multi<String> multi2=multi.subscribe().withSubscriber(processor2);//.toHotStream();

        return CombinationProcessor.of(processor,subscriber);
    }
*/


/*
    public static <T,R> Flow.Processor<T,R> createProcessor(Flow.Publisher<R> publisher,
                                                            Flow.Subscriber<T> subscriber) {

        UnicastProcessor<String> processor = UnicastProcessor.create();
        Multi<String> multi = processor
                .onItem().transform(String::toUpperCase)
                .onFailure().recoverWithItem("d'oh")
                .onItem().invoke(item -> System.out.println("A>> " + item))
                ;//.toHotStream();
        BroadcastProcessor<String> processor2 = BroadcastProcessor.create();
        Multi<String> multi2=multi.subscribe().withSubscriber(processor2);//.toHotStream();

        return CombinationProcessor.of(processor,subscriber);
    }
*/

}
