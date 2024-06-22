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

package com.yelstream.topp.execution.furnace;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Flow;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class CompressionProcessor<T> implements Flow.Publisher<T> {

/*
    private final SubscriberRegistry<T, BaseSubscriber<T,Flow.Subscriber<T>>, BaseSubscription<Flow.Subscription>> subscriberRegistry=new SubscriberRegistry();

    private final Function<T,String> itemToCategoryNameMapper;

    private final BiFunction<T,T,T> itemCompressor;

    private class Category {
        //Queue
    }

    private Map<String,Category> categories=new ConcurrentHashMap<>();
*/


    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        // TODO document why this method is empty
    }

    public Future<T> submit(T item) {
        return null;
    }


}
