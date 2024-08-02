package com.yelstream.topp.furnace.manage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.furnace.manage}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-08-02
 */
@Suite
@SelectClasses({ProcessSpawnManagerTest.class,
                VerticleLifecycleManagerTest.class})
public class ManagementTestSuite {
}
