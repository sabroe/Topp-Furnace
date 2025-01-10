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

package com.yelstream.topp.execution.furnace.simple;

import java.time.Duration;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

public class Main {
    public static void main(String[] args) {
        // Create a publisher
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // Create a processor
        ComputationProcessor processor = new ComputationProcessor();

        // Subscribe the processor to the publisher
        publisher.subscribe(processor);

        // Create a subscriber
        Subscriber<Integer> subscriber = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1); // Request the first element
            }

            @Override
            public void onNext(Integer item) {
                // Process the received item
                System.out.println("Received: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle errors if any
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Processing complete");
            }
        };

        // Subscribe the subscriber to the processor
        processor.subscribe(subscriber);

        // Publish some items
        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);

try {
    Thread.sleep(Duration.ofSeconds(3));
} catch (InterruptedException ex) {
}

        // Close the publisher (to indicate that no more items will be published)
        publisher.close();
    }
}
