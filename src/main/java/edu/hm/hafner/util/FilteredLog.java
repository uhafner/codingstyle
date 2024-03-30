package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.errorprone.annotations.FormatMethod;

/**
 * Provides a log of info messages and a limited number of error messages. If the number of errors exceeds this limit,
 * then subsequent error messages will be skipped. This class is thread-safe and can be used in a distributed
 * environment.
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

    private transient ReentrantLock lock = new ReentrantLock();

    /**
     * Creates a new {@link FilteredLog}. The error messages will not have a pre-defined title, you need to make sure
     * that there is a meaningful title before each error message. The maximum number of printed errors is given
     * by {@link #DEFAULT_MAX_LINES}.
     */
    public FilteredLog() {
        this(StringUtils.EMPTY, DEFAULT_MAX_LINES);
    }

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
     * Creates a new {@link FilteredLog}.
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
     * Called after de-serialization to improve the memory usage.
     *
     * @return this
     */
    protected Object readResolve() {
        lock = new ReentrantLock();

        return this;
    }

    /**
     * Logs the specified information message. Use this method to log any useful information when composing this log.
     *
     * @param message
     *         the message to log
     */
    public void logInfo(final String message) {
        lock.lock();
        try {
            infoMessages.add(message);
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Logs the specified information message. Use this method to log any useful information when composing this log.
     *
     * @param format
     *        a <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string. If there are more arguments than
     *         format specifiers, the extra arguments are ignored. The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logInfo(final String format, final Object... args) {
        logInfo(String.format(format, args));
    }

    /**
     * Logs the specified error message. Use this method to log any error when composing this log.
     *
     * @param message
     *         the error message
     */
    public void logError(final String message) {
        lock.lock();
        try {
            if (lines < maxLines) {
                if (StringUtils.isNotBlank(title) && errorMessages.isEmpty()) {
                    errorMessages.add(title);
                }
                errorMessages.add(message);
            }
            lines++;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Logs the specified error message. Use this method to log any error when composing this log.
     *
     * @param format
     *         a <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string. If there are more arguments than
     *         format specifiers, the extra arguments are ignored. The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logError(final String format, final Object... args) {
        logError(String.format(format, args));
    }

    /**
     * Logs the specified exception. Use this method to log any exception when composing this log.
     *
     * @param exception
     *         the exception to log
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string. If there are more arguments than
     *         format specifiers, the extra arguments are ignored. The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public void logException(final Exception exception, final String format, final Object... args) {
        logError(format, args);

        lock.lock();
        try {
            if (lines <= maxLines) {
                errorMessages.addAll(Arrays.asList(ExceptionUtils.getRootCauseStackTrace(exception)));
            }
        }
        finally {
            lock.unlock();
        }
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
     *
     * @deprecated not useful anymore
     */
    @Deprecated
    public void logSummary() {
        // do nothing
    }

    /**
     * Returns all info messages.
     *
     * @return the info messages
     */
    public List<String> getInfoMessages() {
        lock.lock();
        try {
            return List.copyOf(infoMessages);
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns all error messages up to the configured limit.
     *
     * @return the error messages
     */
    public List<String> getErrorMessages() {
        lock.lock();
        try {
            var messages = new ArrayList<String>();
            if (errorMessages.isEmpty()) {
                return messages;
            }
            messages.addAll(errorMessages);
            if (lines > maxLines) {
                messages.add(String.format("  ... skipped logging of %d additional errors ...", lines - maxLines));
            }
            return messages;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns whether there have been error messages recorded.
     *
     * @return {@code true} if error messages have been recorded, {@code false} otherwise
     */
    public boolean hasErrors() {
        lock.lock();
        try {
            return !errorMessages.isEmpty();
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Merges the info and error messages of the other log.
     *
     * @param other
     *         the log to merge
     */
    public void merge(final FilteredLog other) {
        lock.lock();
        try {
            infoMessages.addAll(other.getInfoMessages());
            errorMessages.addAll(other.getErrorMessages());
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilteredLog log = (FilteredLog) o;

        if (maxLines != log.maxLines) {
            return false;
        }
        if (lines != log.lines) {
            return false;
        }
        if (!Objects.equals(title, log.title)) {
            return false;
        }
        if (!infoMessages.equals(log.infoMessages)) {
            return false;
        }
        return errorMessages.equals(log.errorMessages);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + maxLines;
        result = 31 * result + lines;
        result = 31 * result + infoMessages.hashCode();
        result = 31 * result + errorMessages.hashCode();
        return result;
    }
}
