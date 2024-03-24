/**
 * Topp Execution Injection addressing interoperability with Jakarta CDI.
 */
module com.yelstream.topp.execution.furnace {
    requires static lombok;
    requires org.slf4j;
//    requires io.smallrye.mutiny;
    exports com.yelstream.topp.execution.inject;
}
