package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 * A line range in a source file is defined by its first and last line.
 *
 * @author Ullrich Hafner
 */
public final class LineRange implements Serializable {
    private static final long serialVersionUID = -4124143085672930110L;

    private final int start;
    private final int end;

    /**
     * Creates a new instance of {@link LineRange}.
     *
     * @param line
     *            the single line of this range
     */
    public LineRange(final int line) {
        this(line, line);
    }

    /**
     * Creates a new instance of {@link LineRange}.
     *
     * @param start
     *            start of the range
     * @param end
     *            end of the range
     */
    public LineRange(final int start, final int end) {
        if (start <= 0) {
            this.start = 0;
            this.end = 0;
        }
        else if (start < end) {
            this.start = start;
            this.end = end;
        }
        else {
            this.start = end;
            this.end = start;
        }
    }

    /**
     * Returns the first line of this range.
     *
     * @return the first line of this range
     */
    public int getStart() {
        return start;
    }

    /**
     * Returns the last line of this range.
     *
     * @return the last line of this range
     */
    public int getEnd() {
        return end;
    }

    /**
     * Returns the lines of this line lange in a sorted set.
     *
     * @return the containing lines, one by one
     */
    public NavigableSet<Integer> getLines() {
        var lines = new TreeSet<Integer>();
        for (int line = getStart(); line <= getEnd(); line++) {
            lines.add(line);
        }
        return lines;
    }

    /**
     * Returns whether the specified line is contained in this range.
     *
     * @param line the line to check
     * @return {@code true} if the line is contained in this range, {@code false} otherwise
     */
    public boolean contains(final int line) {
        return line >= start && line <= end;
    }

    /**
     * Returns whether this range is just a single line.
     *
     * @return {@code true} if this range is just a single line, {@code false} otherwise
     */
    public boolean isSingleLine() {
        return start == end;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var lineRange = (LineRange) o;
        return start == lineRange.start
                && end == lineRange.end;
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "[%d-%d]", start, end);
    }
}
