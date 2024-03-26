package com.yelstream.topp.execution.inject.spi;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.aurora.inject.spi}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@Suite
@SelectClasses({BeanManagersTest.class,BeansTest.class})
class InjectionSPITestSuite {
}
