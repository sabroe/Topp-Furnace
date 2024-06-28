package com.yelstream.topp.furnace.reactive.integration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.*;
import java.util.concurrent.Flow.Publisher;

public class CompletionStageToFlowPublisher<T> implements Publisher<T> {
    private final CompletionStage<T> completionStage;

    public CompletionStageToFlowPublisher(CompletionStage<T> completionStage) {
        this.completionStage = completionStage;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        completionStage.whenComplete((result, error) -> {
            if (error != null) {
                subscriber.onError(error);
            } else {
                subscriber.onSubscribe(new Subscription() {
                    private boolean completed = false;

                    @Override
                    public void request(long n) {
                        if (!completed) {
                            if (n > 0) {
                                subscriber.onNext(result);
                                subscriber.onComplete();
                                completed = true;
                            } else {
                                subscriber.onError(new IllegalArgumentException("Demand must be positive"));
                            }
                        }
                    }

                    @Override
                    public void cancel() {
                        completed = true;
                    }
                });
            }
        });
    }
}
