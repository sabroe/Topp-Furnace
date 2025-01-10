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

package com.yelstream.topp.execution.furnace;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        UnicastProcessor<String> processor = UnicastProcessor.create();
        Multi<String> multi = processor
                .onItem().transform(String::toUpperCase)
                .onFailure().recoverWithItem("d'oh")
                .onItem().invoke(item -> System.out.println("A>> " + item))
                ;//.toHotStream();
        BroadcastProcessor<String> processor2 = BroadcastProcessor.create();
        Multi<String> multi2=multi.subscribe().withSubscriber(processor2);//.toHotStream();

        ;

//        Cancellable cancellable1=multi2.subscribe().with(item -> System.out.println(">> " + item));
//        Cancellable cancellable2=multi2.subscribe().with(item -> System.out.println(">> " + item));

// Create a source of items that does not follow the request protocol
        Thread t=new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                processor.onNext(Integer.toString(i));
            }
        });
        t.start();

        t.join();
        processor.onComplete();
    }
}
