package com.yelstream.topp.furnace.manage.vertx;

import com.yelstream.topp.furnace.manage.AbstractManageable;
import com.yelstream.topp.furnace.manage.LifecycleManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Manageable implementation for a Vert.x Verticle.
 *
 * @version 1.0
 * @since 2024-07-29
 */
public class VerticleManageable extends AbstractManageable<Verticle,String,Exception> {

    @Getter
    private final Vertx vertx;

    @Getter
    private final Verticle verticle;

    @lombok.Builder(builderClassName="Builder",access=AccessLevel.PRIVATE)
    private VerticleManageable(Vertx vertx,
                               Verticle verticle,
                               LifecycleManager<Verticle,String,Exception> manager) {
        super(manager);
        this.vertx=vertx;
        this.verticle=verticle;
    }

    public static VerticleManageable of(Verticle verticle) {
        return builder().verticle(verticle).build();
    }

    public static VerticleManageable of(Vertx vertx,
                                        Verticle verticle) {
        return builder().vertx(vertx).verticle(verticle).build();
    }

    private static class Builder {
        public VerticleManageable build() {
            if (vertx==null) {
                vertx=verticle.getVertx();
            }
            if (manager==null) {
                manager=VerticleLifecycleManager.of(vertx,verticle);
            }
            return new VerticleManageable(vertx,verticle,manager);
        }
    }
}
