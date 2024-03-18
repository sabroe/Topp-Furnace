package com.yelstream.topp.execution.util.concurrent.atomic;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility addressing instances of {@link AtomicInteger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class AtomicIntegers {
    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static int updateMin(AtomicInteger minValue,
                                int currentValue) {
        return minValue.updateAndGet(currentMin->Math.min(currentMin,currentValue));
    }

    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static int updateMin(AtomicInteger minValue,
                                AtomicInteger currentValue) {
        return updateMin(minValue,currentValue.get());
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static int updateMax(AtomicInteger maxValue,
                                int currentValue) {
        return maxValue.updateAndGet(currentMax->Math.max(currentMax,currentValue));
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static int updateMax(AtomicInteger maxValue,
                                AtomicInteger currentValue) {
        return updateMax(maxValue,currentValue.get());
    }
}
