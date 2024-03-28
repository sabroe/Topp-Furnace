/**
 * Topp Execution Furnace addressing interaction with traditional Reactive Programming frameworks.
 */
module com.yelstream.topp.execution.furnace {
    requires static lombok;
    requires org.slf4j;
    requires io.smallrye.mutiny;
    exports com.yelstream.topp.execution.lang;
    exports com.yelstream.topp.execution.furnace;
}
