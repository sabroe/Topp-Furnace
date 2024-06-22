package com.yelstream.topp.execution.inject.util;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.aurora.inject.util}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-01
 */
@Suite
@SelectClasses({ConcurrencySniperTest.class})
class InjectionUtilTestSuite {
}
