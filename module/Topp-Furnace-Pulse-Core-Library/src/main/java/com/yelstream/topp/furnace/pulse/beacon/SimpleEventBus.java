package com.yelstream.topp.furnace.pulse.beacon;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleEventBus {
    // Maps to store handlers for each address
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Consumer<Object>>> subscribers = new ConcurrentHashMap<>();

    // Send message to a specific address (Send/Receive pattern)
    public void send(String address, Object message) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            handlers.forEach(handler -> CompletableFuture.runAsync(() -> handler.accept(message)));
        }
    }

    // Request-reply pattern
    public <T> CompletableFuture<T> request(String address, Object message) {
        CompletableFuture<T> future = new CompletableFuture<>();
        send(address, new RequestMessage<>(message, future));
        return future;
    }

    // Publish message to all subscribers of an address (Publish-Subscribe pattern)
    public void publish(String address, Object message) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            handlers.forEach(handler -> CompletableFuture.runAsync(() -> handler.accept(message)));
        }
    }

    // Register a handler to a specific address
    public void register(String address, Consumer<Object> handler) {
        subscribers.computeIfAbsent(address, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    // Unregister a handler from a specific address
    public void unregister(String address, Consumer<Object> handler) {
        var handlers = subscribers.get(address);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                subscribers.remove(address);
            }
        }
    }

    // Internal class for request message with future
    private static class RequestMessage<T> {
        final Object message;
        final CompletableFuture<T> future;

        RequestMessage(Object message, CompletableFuture<T> future) {
            this.message = message;
            this.future = future;
        }
    }

    // Example of a handler for request-reply
    public <T> void registerRequestHandler(String address, Function<Object, T> handler) {
        register(address, msg -> {
            RequestMessage<T> requestMessage = (RequestMessage<T>) msg;
            T result = handler.apply(requestMessage.message);
            requestMessage.future.complete(result);
        });
    }



    public static void main(String[] args) {
        SimpleEventBus eventBus = new SimpleEventBus();

        // Example: Register a handler for simple send/receive
        eventBus.register("print", message -> System.out.println("Received: " + message));

        // Example: Send a message
        eventBus.send("print", "Hello, World!");

        // Example: Register a handler for request-reply
        eventBus.registerRequestHandler("square", message -> {
            int number = (Integer) message;
            return number * number;
        });

        // Example: Request a reply
        eventBus.request("square", 4).thenAccept(result -> System.out.println("Result: " + result));

        // Example: Register a handler for publish-subscribe
        eventBus.register("news", message -> System.out.println("Subscriber 1 received news: " + message));
        eventBus.register("news", message -> System.out.println("Subscriber 2 received news: " + message));

        // Example: Publish a message
        eventBus.publish("news", "Breaking News!");
    }
}
