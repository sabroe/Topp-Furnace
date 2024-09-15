package com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer;

/**
 * Provides access to the data space of a buffer.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-10
 */
public interface Space {
    /**
     * Gets the length of the space.
     */
    int getLength();

    /**
     * Gets read access to the space.
     * @return Read access.
     */
    Gettable getGettable();

    /**
     * Gets write access to the space.
     * @return Write access.
     */
    Puttable getPuttable();

    //TO-DO: Consider adding expansion functionality!

    //TO-DO: Consider adding slicing functionality!

/*
    int length()

    Buffer copy()

    Buffer slice()

    Buffer slice(int start,
                 int end)
*/
}
