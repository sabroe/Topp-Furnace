package com.yelstream.topp.execution.concurrent.executor;

import com.yelstream.topp.standard.lang.Runnables;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executor;

/**
 * Standard executor manager.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
@RequiredArgsConstructor(staticName="of")
public class StandardExecutorManager implements ExecutorManager {
    /**
     * Executor.
     */
    private final Executor executor;

    /**
     * Close operation.
     * This may be {@link null}.
     */
    private final Runnable close;

    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }

    @Override
    public void close() throws Exception {
        Runnables.run(close);
    }

    public static StandardExecutorManager of(Executor executor) {
        return of(executor,null);
    }
}
