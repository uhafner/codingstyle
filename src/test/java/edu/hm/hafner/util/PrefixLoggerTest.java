package edu.hm.hafner.util;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link PrefixLogger}.
 *
 * @author Ullrich Hafner
 */
class PrefixLoggerTest {
    private static final String LOG_MESSAGE = "Hello PrefixLogger!";
    private static final String TOOL_NAME = "test";
    private static final String EXPECTED_TOOL_PREFIX = "[test]";
    private static final String FIRST_MESSAGE = "One";
    private static final String SECOND_MESSAGE = "Two";

    @Test @SuppressWarnings("PMD.CloseResource")
    void shouldLogSingleAndMultipleLines() {
        PrintStream printStream = mock(PrintStream.class);
        PrefixLogger logger = new PrefixLogger(printStream, TOOL_NAME);

        logger.log(LOG_MESSAGE);

        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + LOG_MESSAGE);

        PrefixLogger loggerWithBraces = new PrefixLogger(printStream, EXPECTED_TOOL_PREFIX);

        loggerWithBraces.log(LOG_MESSAGE);

        verify(printStream, times(2)).println(EXPECTED_TOOL_PREFIX + " " + LOG_MESSAGE);

        logger.logEachLine(emptyList());

        verifyNoMoreInteractions(printStream);

        logger.logEachLine(singletonList(FIRST_MESSAGE));

        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + FIRST_MESSAGE);

        logger.logEachLine(asList(FIRST_MESSAGE, SECOND_MESSAGE));
        verify(printStream, times(2)).println(EXPECTED_TOOL_PREFIX + " " + FIRST_MESSAGE);
        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + SECOND_MESSAGE);
    }
}
