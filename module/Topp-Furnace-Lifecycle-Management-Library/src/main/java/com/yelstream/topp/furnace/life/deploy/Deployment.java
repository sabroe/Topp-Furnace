package com.yelstream.topp.furnace.life.deploy;

import com.yelstream.topp.furnace.life.deploy.op.Deployable;
import com.yelstream.topp.furnace.life.deploy.op.Undeployable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

public interface Deployment<S,E extends Exception> extends Undeployable<S,E> {



}
