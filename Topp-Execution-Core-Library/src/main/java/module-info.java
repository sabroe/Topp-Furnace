/**
 * Topp Execution core addressing basics related to threads, concurrency, basic asynchronous computation.
 */
module com.yelstream.topp.execution.core {
    requires static lombok;
    requires org.slf4j;
    exports com.yelstream.topp.execution.concurrent.atomic;
    exports com.yelstream.topp.execution.concurrent.flow;
    exports com.yelstream.topp.execution.thread;
    exports com.yelstream.topp.execution.thread.operation;
    exports com.yelstream.topp.execution.thread.holder;
    exports com.yelstream.topp.execution.concurrent.tool;
}
