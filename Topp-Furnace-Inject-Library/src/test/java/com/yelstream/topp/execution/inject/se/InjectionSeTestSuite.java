package com.yelstream.topp.execution.inject.se;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.aurora.inject.se}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@Suite
@SelectClasses({SeContainersTest.class})
class InjectionSeTestSuite {
}
