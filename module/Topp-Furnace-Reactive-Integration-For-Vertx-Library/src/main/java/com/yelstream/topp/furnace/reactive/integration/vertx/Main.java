package com.yelstream.topp.furnace.reactive.integration.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.streams.ReadStream;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Example ReadStream from a file
        vertx.fileSystem().open("example.txt", new OpenOptions(), result -> {
            if (result.succeeded()) {
                AsyncFile file = result.result();
                ReadStream<Buffer> readStream = file;

                // Convert ReadStream to Flow.Publisher
                Flow.Publisher<Buffer> publisher = new ReadStreamToFlowPublisher<>(readStream);

                // Convert Flow.Publisher to ReadStream
                ReadStream<Buffer> convertedReadStream = new FlowPublisherToReadStream<>(publisher);

                // Using the converted ReadStream
                convertedReadStream.handler(buffer -> {
                    System.out.println("Received: " + buffer.toString(StandardCharsets.UTF_8));
                }).exceptionHandler(Throwable::printStackTrace).endHandler(v -> System.out.println("Stream ended"));

                // Simulate data input for demonstration (not needed for real file)
                SubmissionPublisher<Buffer> submissionPublisher = (SubmissionPublisher<Buffer>) publisher;
                submissionPublisher.submit(Buffer.buffer("Hello\n"));
                submissionPublisher.submit(Buffer.buffer("World\n"));
                submissionPublisher.close();
            } else {
                System.out.println("Failed to open file: " + result.cause());
            }
        });
    }
}
