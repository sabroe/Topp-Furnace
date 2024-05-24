package com.yelstream.topp.execution.inject.util.sniper;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@ConcurrencySniper(id="M1")
public class ManagedBean1 {
    public void operation1() {
        log.info("Called is operation 1.");
        operation2();
    }

    public void operation2() {
        log.info("Called is operation 2.");
    }
}
