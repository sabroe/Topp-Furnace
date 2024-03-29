/**
 * Topp Execution Furnace addressing interaction with traditional Reactive Programming frameworks.
 */
module com.yelstream.topp.execution.furnace {
    requires static lombok;
    requires java.base;
    requires org.slf4j;
    requires io.smallrye.mutiny;
    requires com.yelstream.topp.standard.core;
    requires com.yelstream.topp.execution.core;
//    requires com.yelstream.topp.execution.concurrent.future;
//    exports com.yelstream.topp.execution.lang;
    exports com.yelstream.topp.execution.furnace;
}
