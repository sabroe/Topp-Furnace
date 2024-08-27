package com.yelstream.topp.furnace.vertx.core.manage;

import com.yelstream.topp.furnace.manage.Manageable;
import com.yelstream.topp.furnace.vertx.core.Promises;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 * Manageable implementation for a Vert.x Verticle.
 *
 * @version 1.0
 * @since 2024-08-27
 */
@RequiredArgsConstructor(staticName="of")
public class ManageableVerticle<S,T,E extends Exception> extends AbstractVerticle {

    @Getter
    private final Manageable<S,T,E> manageable;

    @Override
    public void start(Promise<Void> promise) throws Exception {
        CompletableFuture<S> future=manageable.getManager().start();
        Promises.tiePromiseToVoid(promise,future);
    }

    @Override
    public void stop(Promise<Void> promise) throws Exception {
        CompletableFuture<T> future=manageable.getManager().stop();
        Promises.tiePromiseToVoid(promise,future);
    }
}
