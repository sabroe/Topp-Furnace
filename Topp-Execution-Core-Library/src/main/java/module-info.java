/**
 * Topp Execution Core addressing basics related to threads, concurrency, basic asynchronous computation.
 */
module com.yelstream.topp.execution.core {
    requires static lombok;
    requires java.base;
    requires org.slf4j;
    requires com.yelstream.topp.standard.core;
    exports com.yelstream.topp.execution.concurrent.atomic;
    exports com.yelstream.topp.execution.concurrent.future;
    exports com.yelstream.topp.execution.concurrent.flow;
    exports com.yelstream.topp.execution.thread;
    exports com.yelstream.topp.execution.thread.operation;
    exports com.yelstream.topp.execution.thread.holder;
    exports com.yelstream.topp.execution.tool;
}
