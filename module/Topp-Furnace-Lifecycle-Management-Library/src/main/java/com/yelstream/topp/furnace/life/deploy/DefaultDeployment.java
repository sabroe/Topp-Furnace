package com.yelstream.topp.furnace.life.deploy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(access= AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class DefaultDeployment<S,E extends Exception> implements Deployment<S,E> {

    @Override
    public CompletableFuture<S> undeploy() throws E {
        return null;
    }
}
