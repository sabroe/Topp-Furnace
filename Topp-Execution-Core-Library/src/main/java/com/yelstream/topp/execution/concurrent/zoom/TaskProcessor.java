package com.yelstream.topp.execution.concurrent.zoom;

public interface TaskProcessor {  //TODO: TaskManager??

    InvokeManager getInvokeManager();  //TODO: invoker() ?


    SubmitManager getSubmitManager();  //TODO: submitter() ?

}
