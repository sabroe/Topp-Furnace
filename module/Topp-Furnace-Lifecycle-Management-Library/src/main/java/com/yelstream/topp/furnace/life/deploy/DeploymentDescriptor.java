package com.yelstream.topp.furnace.life.deploy;

import com.yelstream.topp.furnace.life.deploy.op.Deployable;

public interface DeploymentDescriptor<S,D extends Deployment<S,E>,E extends Exception> extends Deployable<D,E> {
}
