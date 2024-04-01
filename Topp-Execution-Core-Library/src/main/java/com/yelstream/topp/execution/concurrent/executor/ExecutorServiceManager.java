package com.yelstream.topp.execution.concurrent.executor;

import java.util.concurrent.ExecutorService;

/**
 * Executor service manager.
 * <p>
 *     The signature is inspired by {@link ExecutorService}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-01
 */
public interface ExecutorServiceManager extends Shutdownable, AutoCloseable {


    InvokeManager getInvokeManager();


    SubmitManager getSubmitManager();
}
