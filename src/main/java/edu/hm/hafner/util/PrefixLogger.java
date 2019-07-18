package edu.hm.hafner.util;

import java.io.PrintStream;
import java.util.Collection;

import com.google.errorprone.annotations.FormatMethod;

/**
 * A simple logger that prefixes each message with the a given name.
 *
 * @author Ullrich Hafner
 */
class PrefixLogger {
    private final String toolName;
    private final PrintStream delegate;

    PrefixLogger(final PrintStream logger, final String toolName) {
        if (toolName.contains("[")) {
            this.toolName = toolName + " ";
        }
        else {
            this.toolName = String.format("[%s] ", toolName);
        }
        delegate = logger;
    }

    /**
     * Logs the specified message.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    void log(final String format, final Object... args) {
        print(String.format(format, args));
    }

    /**
     * Logs the specified messages.
     *
     * @param lines
     *         the messages to log
     */
    void logEachLine(final Collection<String> lines) {
        lines.forEach(this::print);
    }

    private void print(final String line) {
        delegate.println(toolName + line);
    }
}
