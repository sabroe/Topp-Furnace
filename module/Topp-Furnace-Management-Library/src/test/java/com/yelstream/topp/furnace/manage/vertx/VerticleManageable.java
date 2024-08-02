package com.yelstream.topp.furnace.manage.vertx;

import com.yelstream.topp.furnace.manage.AbstractManageable;
import com.yelstream.topp.furnace.manage.LifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;

/**
 * Manageable implementation for a Vert.x Verticle.
 *
 * @version 1.0
 * @since 2024-07-29
 */
//@RequiredArgsConstructor
public class VerticleManageable extends AbstractManageable<Verticle,Void,Exception> {
    private final Vertx vertx;
    private final Verticle verticle;

    public VerticleManageable(LifecycleManager<Verticle,Void,Exception> manager,
                              Vertx vertx,
                              Verticle verticle) {
        super(manager);
        this.vertx = vertx;
        this.verticle = verticle;
    }

    public VerticleManageable(Vertx vertx,
                              Verticle verticle) {
        super(new VerticleLifecycleManager(vertx,verticle));
        this.vertx = vertx;
        this.verticle = verticle;
    }
}
