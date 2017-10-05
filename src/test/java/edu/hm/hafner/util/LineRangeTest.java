package edu.hm.hafner.util;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the class {@link LineRange}.
 *
 * @author Ullrich Hafner
 */
public class LineRangeTest {
    @Test(expected = AssertionError.class)
    public void testConstructorSingleLineNegative() throws Exception {
        createLineRange(-1);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorSingleLineZero() throws Exception {
        createLineRange(0);
    }

    @Test
    public void testConstructorAndSizeSingleLine() throws Exception {
        LineRange lineRange = createLineRange(1);

        assertThat(lineRange.size(), is(1));
    }

    @Test(expected = AssertionError.class)
    public void testConstructorMultipleLinesFromNegative() throws Exception {
        createLineRange(-1, 3);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorMultipleLinesFromZero() throws Exception {
        createLineRange(0, 3);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorMultipleLinesToNegative() throws Exception {
        createLineRange(3, -1);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorMultipleLinesToZero() throws Exception {
        createLineRange(3, 0);
    }

    @Test
    public void testConstructorAndSizeMultipleLinesSmallestLineNumber() throws Exception {
        LineRange lineRange = createLineRange(1, 1);

        assertThat(lineRange.size(), is(1));
    }

    @Test
    public void testConstructorAndSizeMultipleLines() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertThat(lineRange.size(), is(3));
    }

    @Test
    public void testConstructorAndSizeMultipleLinesFromToSwapped() throws Exception {
        LineRange lineRange = createLineRange(3, 1);

        assertThat(lineRange.size(), is(3));
    }

    @Test
    public void testValuesSingleLine() throws Exception {
        LineRange lineRange = createLineRange(1);

        assertThat(lineRange.values(), equalTo(new int[]{1}));
    }

    @Test
    public void testValuesMultipleLines() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertThat(lineRange.values(), equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testToStringSingleLine() throws Exception {
        LineRange lineRange = createLineRange(1);

        assertThat(lineRange.toString(), equalTo("{1}"));
    }

    @Test
    public void testToStringMultipleLines() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertThat(lineRange.toString(), equalTo("{1, ..., 3}"));
    }

    @Test
    public void testToStringTwoLines() throws Exception {
        LineRange lineRange = createLineRange(1, 2);

        assertThat(lineRange.toString(), equalTo("{1, 2}"));
    }

    @Test
    public void testIntersectLineRangeIntersectsItself() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertTrue(lineRange.intersects(lineRange));
    }

    @Test
    public void testIntersectsPartialIntersectionOnEnds() throws Exception {
        LineRange lineRange1 = createLineRange(1, 3);
        LineRange lineRange2 = createLineRange(3, 5);

        assertTrue(lineRange1.intersects(lineRange2));
    }

    @Test
    public void testIntersectsPartialIntersectionWithSingleLine() throws Exception {
        LineRange lineRange1 = createLineRange(1, 3);
        LineRange lineRange2 = createLineRange(2);

        assertTrue(lineRange1.intersects(lineRange2));
    }

    @Test
    public void testIntersectsOneLineApartNoIntersection() throws Exception {
        LineRange lineRange1 = createLineRange(1, 3);
        LineRange lineRange2 = createLineRange(4, 6);

        assertFalse(lineRange1.intersects(lineRange2));
    }

    @Test
    public void testContainsBeginning() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertTrue(lineRange.contains(1));
    }

    @Test
    public void testContainsEnd() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertTrue(lineRange.contains(3));
    }

    @Test
    public void testContainsNegative() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertFalse(lineRange.contains(4));
    }

    @Test
    public void testContainsLineRangeContainsItself() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertTrue(lineRange.contains(lineRange));
    }

    @Test
    public void testContainsLargerContainsSmaller() throws Exception {
        LineRange larger = createLineRange(1, 5);
        LineRange smaller = createLineRange(1, 3);

        assertTrue(larger.contains(smaller));
    }

    @Test
    public void testContainsSmallerNotContainsLarger() throws Exception {
        LineRange larger = createLineRange(1, 5);
        LineRange smaller = createLineRange(1, 3);

        assertFalse(smaller.contains(larger));
    }

    @Test
    public void testIteratorSingleLine() throws Exception {
        LineRange lineRange = createLineRange(1);

        assertThat(lineRange, contains(1));
    }

    @Test
    public void testIteratorMultipleLine() throws Exception {
        LineRange lineRange = createLineRange(1, 3);

        assertThat(lineRange, contains(1, 2, 3));
    }

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