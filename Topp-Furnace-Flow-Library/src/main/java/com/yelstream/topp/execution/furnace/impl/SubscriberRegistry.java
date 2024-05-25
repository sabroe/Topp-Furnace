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

package com.yelstream.topp.execution.furnace.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;

public class SubscriberRegistry<T,S extends Flow.Subscriber<T>,P extends Flow.Subscription> {

    private final Map<S,SubscriberInfo<T,S,P>> info=new ConcurrentHashMap<>();

    public int getSubscriberCount() {
        return info.size();
    }

    public void add(S subscriber) {
        // TODO document why this method is empty
    }

    public void remove(S subscriber) {
        // TODO document why this method is empty
    }

}
