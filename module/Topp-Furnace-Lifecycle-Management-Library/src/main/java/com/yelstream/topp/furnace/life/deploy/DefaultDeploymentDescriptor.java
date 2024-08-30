package com.yelstream.topp.furnace.life.deploy;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(access= AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class DefaultDeploymentDescriptor<S,D extends Deployment<S,E>,E extends Exception> implements DeploymentDescriptor<S,D,E> {

    @Override
    public CompletableFuture<D> deploy() throws E {
        return null;
    }
}
