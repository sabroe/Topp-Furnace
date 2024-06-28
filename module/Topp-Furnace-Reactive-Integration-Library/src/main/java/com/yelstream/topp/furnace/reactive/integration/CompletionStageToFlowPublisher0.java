package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletionStage;

public class CompletionStageToFlowPublisher0<T> implements Flow.Publisher<T> {

    private final SubmissionPublisher<T> publisher = new SubmissionPublisher<>();

    public CompletionStageToFlowPublisher0(CompletionStage<T> completionStage) {
        completionStage.whenComplete((result, error) -> {
            if (error != null) {
                publisher.closeExceptionally(error);
            } else {
                publisher.submit(result);
                publisher.close();
            }
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(subscriber);
    }
}
