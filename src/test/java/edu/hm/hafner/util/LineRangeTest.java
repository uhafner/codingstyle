package edu.hm.hafner.util;

/**
 * Tests the class {@link LineRange}.
 *
 * @author Ullrich Hafner
 */
public class LineRangeTest {
    protected LineRange createLineRange(final int from, final int to) {
        return new LineRange(from, to);
    }

    protected LineRange createLineRange(final int line) {
        return new LineRange(line);
    }
}