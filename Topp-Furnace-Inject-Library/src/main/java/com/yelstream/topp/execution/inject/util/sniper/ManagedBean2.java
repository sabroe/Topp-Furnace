package com.yelstream.topp.execution.inject.util.sniper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@ConcurrencySniper(id="M2")
@RequiredArgsConstructor(onConstructor_=@Inject)
public class ManagedBean2 {
    @Getter
    private final ManagedBean1 managedBean1;

    public void operationA() {
        log.info("Called is operation A; managedBean1={}.",managedBean1);
    }

    public void operationB() {
        log.info("Called is operation B; managedBean1={}.",managedBean1);
    }
}
