package com.yelstream.topp.execution.tool;

import com.yelstream.topp.execution.concurrent.atomic.AtomicIntegers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracker of invocation count.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-05
 */
@NoArgsConstructor(staticName="of")
@ToString
public class InvocationCountTracker {
    private final AtomicLong accumulated=new AtomicLong();
    private final AtomicInteger active=new AtomicInteger();
    private final AtomicInteger maximumActive=new AtomicInteger();

    public final class Reader {
        public long accumulated() {
            return accumulated.get();
        }

        public int active() {
            return active.get();
        }

        public int maximumActive() {
            return maximumActive.get();
        }
    }

    @Getter
    private final Reader reader=new Reader();

    protected int invocationBegin() {
        accumulated.incrementAndGet();
        @SuppressWarnings("java:S1117")
        int active=this.active.incrementAndGet();
        AtomicIntegers.updateMax(maximumActive,active);
        return active;
    }

    protected void invocationEnd() {
        active.decrementAndGet();
    }

    @Getter
    public final class Invocation implements AutoCloseable {
        private final int active;

        private Invocation() {
            active=invocationBegin();
        }

        @Override
        public void close() {
            invocationEnd();
        }
    }

    public Invocation count() {
        return new Invocation();
    }
}
