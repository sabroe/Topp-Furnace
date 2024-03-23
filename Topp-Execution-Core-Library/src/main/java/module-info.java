/**
 * Topp Execution core module.
 */
module com.yelstream.topp.execution.core {
    requires static lombok;
    requires org.slf4j;
    exports com.yelstream.topp.execution.concurrent.atomic;
    exports com.yelstream.topp.execution.concurrent.flow;
    exports com.yelstream.topp.execution.thread;
    exports com.yelstream.topp.execution.thread.operation;
    exports com.yelstream.topp.execution.thread.holder;
}
