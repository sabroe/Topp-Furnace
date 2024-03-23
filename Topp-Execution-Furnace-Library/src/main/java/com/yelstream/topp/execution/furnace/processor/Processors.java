package com.yelstream.topp.execution.furnace.processor;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Flow;

@UtilityClass
public class Processors {

    public static <R,T,P extends Flow.Publisher<R>,S extends Flow.Subscriber<T>,H> TypedProcessor<R,T,P,S,H> createProcessor(H tie,
                                                                                                                             P publisher,
                                                                                                                             S subscriber) {
        return TypedProcessor.of(tie,publisher,subscriber);
    }

    public static <R,P extends Flow.Publisher<R>> TypedProcessor<R,R,P,BroadcastProcessor<R>,Multi<R>> createProcessor(P publisher) {
        BroadcastProcessor<R> processor=BroadcastProcessor.create();
        Multi<R> multi=Multi
            .createFrom().publisher(publisher)
            .subscribe().withSubscriber(processor)
            .toHotStream();
        return TypedProcessor.of(multi,publisher,processor);
    }


    public static <R> TypedProcessor<R,R,Flow.Publisher<R>,UnicastProcessor<R>,Multi<R>> createProcessor() {
        UnicastProcessor<R> processor=UnicastProcessor.create();
        BroadcastProcessor<R> broadcastProcessor=BroadcastProcessor.create();
        Multi<R> multi=processor
            .onItem().invoke(item -> System.out.println("A>> " + item))
//            .toHotStream()
            .subscribe().withSubscriber(broadcastProcessor)
;//            .toHotStream();
        return TypedProcessor.of(multi,broadcastProcessor,processor);
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
