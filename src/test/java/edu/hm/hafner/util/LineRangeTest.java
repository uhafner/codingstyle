package edu.hm.hafner.util;

/**
 * Tests the class {@link LineRange}.
 *
 * @author Ullrich Hafner
 */
public class LineRangeTest {
    /**
     * Creates a new {@link LineRange}. The specified starting and ending lines are included in this range.
     *
     * @param from the first line of this range
     * @param to   the last line of this range
     * @return the created SUT
     */
    protected LineRange createLineRange(final int from, final int to) {
        return new LineRange(from, to);
    }

    /**
     * Creates a new {@link LineRange} containing only one line.
     *
     * @param line the single line of this range
     * @return the created SUT
     */
    protected LineRange createLineRange(final int line) {
        return new LineRange(line);
    }
}