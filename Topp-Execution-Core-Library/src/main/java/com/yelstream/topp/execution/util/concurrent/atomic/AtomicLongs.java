package com.yelstream.topp.execution.util.concurrent.atomic;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility addressing instances of {@link AtomicLong}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class AtomicLongs {
    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static long updateMin(AtomicLong minValue,
                                 long currentValue) {
        return minValue.updateAndGet(currentMin->Math.min(currentMin,currentValue));
    }

    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static long updateMin(AtomicLong minValue,
                                 AtomicLong currentValue) {
        return updateMin(minValue,currentValue.get());
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static long updateMax(AtomicLong maxValue,
                                 long currentValue) {
        return maxValue.updateAndGet(currentMax->Math.max(currentMax,currentValue));
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static long updateMax(AtomicLong maxValue,
                                 AtomicLong currentValue) {
        return updateMax(maxValue,currentValue.get());
    }
}
