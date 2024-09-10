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

package com.yelstream.topp.furnace.pulse.beacon;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleEventBus {
    // Maps to store handlers for each address with type safety
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Consumer<?>>> subscribers = new ConcurrentHashMap<>();

    // Send message to a specific address (Send/Receive pattern)
    public <T> void send(String address, T message) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            for (Consumer<?> handler : handlers) {
                @SuppressWarnings("unchecked")
                Consumer<T> castedHandler = (Consumer<T>) handler;
                CompletableFuture.runAsync(() -> castedHandler.accept(message));
            }
        }
    }

    // Request-reply pattern
    public <T, R> CompletableFuture<R> request(String address, T message) {
        CompletableFuture<R> future = new CompletableFuture<>();
        send(address, new RequestMessage<>(message, future));
        return future;
    }

    // Publish message to all subscribers of an address (Publish-Subscribe pattern)
    public <T> void publish(String address, T message) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            for (Consumer<?> handler : handlers) {
                @SuppressWarnings("unchecked")
                Consumer<T> castedHandler = (Consumer<T>) handler;
                CompletableFuture.runAsync(() -> castedHandler.accept(message));
            }
        }
    }

    // Register a handler to a specific address
    public <T> void register(String address, Consumer<T> handler) {
        subscribers.computeIfAbsent(address, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    // Unregister a handler from a specific address
    public <T> void unregister(String address, Consumer<T> handler) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                subscribers.remove(address);
            }
        }
    }

    // Internal class for request message with future
    private static class RequestMessage<T, R> {
        final T message;
        final CompletableFuture<R> future;

        RequestMessage(T message, CompletableFuture<R> future) {
            this.message = message;
            this.future = future;
        }
    }

    // Register a handler for request-reply with type safety
    public <T, R> void registerRequestHandler(String address, Function<T, R> handler) {
        register(address, msg -> {
            @SuppressWarnings("unchecked")
            RequestMessage<T, R> requestMessage = (RequestMessage<T, R>) msg;
            R result = handler.apply(requestMessage.message);
            requestMessage.future.complete(result);
        });
    }

    public static void main(String[] args) {
        SimpleEventBus eventBus = new SimpleEventBus();

        // Example: Register a handler for simple send/receive
        eventBus.register("print", (String message) -> System.out.println("Received: " + message));

        // Example: Send a message
        eventBus.send("print", "Hello, World!");

        // Example: Register a handler for request-reply
        eventBus.registerRequestHandler("square", (Integer number) -> number * number);

        // Example: Request a reply
        eventBus.request("square", 4).thenAccept(result -> System.out.println("Result: " + result));

        // Example: Register a handler for publish-subscribe
        eventBus.register("news", (String message) -> System.out.println("Subscriber 1 received news: " + message));
        eventBus.register("news", (String message) -> System.out.println("Subscriber 2 received news: " + message));

        // Example: Publish a message
        eventBus.publish("news", "Breaking News!");
    }
}
