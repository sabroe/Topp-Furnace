/**
 * Topp Execution Injection addressing interoperability with Jakarta CDI.
 */
module com.yelstream.topp.execution.injection {
    requires static lombok;
    requires org.slf4j;
    requires jakarta.cdi;
    requires com.yelstream.topp.execution.core;
    exports com.yelstream.topp.execution.cdi.interceptor;
    exports com.yelstream.topp.execution.inject.se;
    exports com.yelstream.topp.execution.inject.spi;
    exports com.yelstream.topp.execution.inject.util.sniper;
}
