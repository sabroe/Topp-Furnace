/**
 * Topp Execution furnace module.
 * This is specific for reactive programming.
 */
module com.yelstream.topp.execution.furnace {
    requires static lombok;
    requires org.slf4j;
    requires io.smallrye.mutiny;
    exports com.yelstream.topp.execution.furnace;
}
