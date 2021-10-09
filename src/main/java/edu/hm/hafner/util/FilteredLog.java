package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.errorprone.annotations.FormatMethod;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Provides a log of info messages and a limited number of error messages. If the number of errors exceeds this limit,
 * then subsequent error messages will be skipped.
 *
 * @author Ullrich Hafner
 */
public class FilteredLog implements Serializable {
    private static final long serialVersionUID = -8552323621953159904L;

    private static final int DEFAULT_MAX_LINES = 20;

    private final String title;
    private final int maxLines;
    private int lines = 0;

    private final List<String> infoMessages = new ArrayList<>();
    private final List<String> errorMessages = new ArrayList<>();

    /**
     * Creates a new {@link FilteredLog}. The maximum number of printed errors is given by {@link #DEFAULT_MAX_LINES}.
     *
     * @param title
     *         the title of the error messages
     */
    public FilteredLog(final String title) {
        this(title, DEFAULT_MAX_LINES);
    }

    /**
     * Creates a new {@link FilteredLog}..
     *
     * @param title
     *         the title of the error messages
     * @param maxLines
     *         the maximum number of lines to log
     */
    public FilteredLog(final String title, final int maxLines) {
        this.title = title;
        this.maxLines = maxLines;
    }

    /**
     * Logs the specified information message. Use this method to log any useful information when composing this log.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logInfo(final String format, final Object... args) {
        infoMessages.add(String.format(format, args));
    }

    /**
     * Logs the specified error message. Use this method to log any error when composing this log.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logError(final String format, final Object... args) {
        printTitle();

        if (lines < maxLines) {
            errorMessages.add(String.format(format, args));
        }
        lines++;
    }

    private void printTitle() {
        if (lines == 0) {
            errorMessages.add(title);
        }
    }

    /**
     * Logs the specified exception. Use this method to log any exception when composing this log.
     *
     * @param exception
     *         the exception to log
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logException(final Exception exception, final String format, final Object... args) {
        printTitle();

        if (lines < maxLines) {
            errorMessages.add(String.format(format, args));
            errorMessages.addAll(Arrays.asList(ExceptionUtils.getRootCauseStackTrace(exception)));
        }
        lines++;
    }

    /**
     * Returns the total number of errors that have been reported.
     *
     * @return the total number of errors
     */
    public int size() {
        return lines;
    }

    /**
     * Writes a summary message to the reports' error log that denotes the total number of errors that have been
     * reported.
     */
    public void logSummary() {
        if (lines > maxLines) {
            errorMessages.add(String.format("  ... skipped logging of %d additional errors ...", lines - maxLines));
        }
    }

    /**
     * Returns the info messages that have been reported since the creation of this set of issues.
     *
     * @return the info messages
     */
    public List<String> getInfoMessages() {
        return Collections.unmodifiableList(infoMessages);
    }

    /**
     * Returns the error messages that have been reported since the creation of this set of issues.
     *
     * @return the error messages
     */
    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }

    /**
     * Merges the info and error messages of the other log.
     *
     * @param other
     *         the log to merge
     */
    public void merge(final FilteredLog other) {
        infoMessages.addAll(other.infoMessages);
        errorMessages.addAll(other.errorMessages);
    }

    @Override @Generated
    public boolean equals(@CheckForNull final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilteredLog that = (FilteredLog) o;
        return maxLines == that.maxLines && lines == that.lines && title.equals(that.title)
                && infoMessages.equals(that.infoMessages)
                && errorMessages.equals(that.errorMessages);
    }

    @Override @Generated
    public int hashCode() {
        return Objects.hash(title, maxLines, lines, infoMessages, errorMessages);
    }

    @Override @Generated
    public String toString() {
        return "FilteredLog{"
                + "title='" + title + '\''
                + ", maxLines=" + maxLines
                + ", lines=" + lines
                + ", infoMessages=" + infoMessages
                + ", errorMessages=" + errorMessages
                + '}';
    }
}
