package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static edu.hm.hafner.util.assertions.Assertions.*;

/**
 * Tests the class {@link FilteredLog}.
 *
 * @author Ullrich Hafner
 */
class FilteredLogTest extends SerializableTest<FilteredLog> {
    private static final String TITLE = "Title: ";

    @Test
    void shouldLogNothing() {
        FilteredLog filteredLog = new FilteredLog(TITLE, 5);

        assertThat(filteredLog).hasNoErrorMessages();
        filteredLog.logSummary();
        assertThat(filteredLog).hasNoErrorMessages();
    }

    @Test
    void shouldLogAllErrors() {
        FilteredLog filteredLog = new FilteredLog(TITLE, 5);

        filteredLog.logError("1");
        filteredLog.logError("2");
        filteredLog.logError("3");
        filteredLog.logError("4");
        filteredLog.logError("5");

        assertThatExactly5MessagesAreLogged(filteredLog);

        filteredLog.logSummary();

        assertThatExactly5MessagesAreLogged(filteredLog);
        assertThat(filteredLog.size()).isEqualTo(5);
    }

    @Test
    void shouldSkipAdditionalErrors() {
        FilteredLog filteredLog = new FilteredLog(TITLE, 5);

        filteredLog.logError("1");
        filteredLog.logError("2");
        filteredLog.logError("3");
        filteredLog.logError("4");
        filteredLog.logError("5");
        filteredLog.logError("6");
        filteredLog.logError("7");

        assertThatExactly5MessagesAreLogged(filteredLog);

        filteredLog.logSummary();

        assertThat(filteredLog).hasOnlyErrorMessages(TITLE, "1", "2", "3", "4", "5",
                "  ... skipped logging of 2 additional errors ...");
        assertThat(filteredLog.size()).isEqualTo(7);
    }

    private void assertThatExactly5MessagesAreLogged(final FilteredLog filteredLog) {
        assertThat(filteredLog).hasOnlyErrorMessages(TITLE, "1", "2", "3", "4", "5");
    }

    @Test
    void shouldMergeLogger() {
        FilteredLog parent = new FilteredLog("Parent Errors");

        parent.logInfo("parent Info 1");
        parent.logError("parent Error 1");

        FilteredLog child = new FilteredLog("Child Errors");
        child.logInfo("child Info 1");
        child.logError("child Error 1");

        parent.merge(child);

        assertThat(parent).hasOnlyInfoMessages("parent Info 1", "child Info 1");
        assertThat(parent).hasOnlyErrorMessages("Parent Errors", "parent Error 1", "Child Errors", "child Error 1");
    }

    @Test
    void shouldLogExceptions() {
        FilteredLog filteredLog = new FilteredLog(TITLE, 1);

        filteredLog.logException(new IllegalArgumentException("Cause"), "Message");
        filteredLog.logException(new IllegalArgumentException(""), "Message");

        assertThat(filteredLog).hasErrorMessages(TITLE,
                "Message", "java.lang.IllegalArgumentException: Cause");
    }

    @Test
    void shouldLog20ErrorsByDefault() {
        FilteredLog filteredLog = createLogWith20Elements();

        assertThat(filteredLog.getErrorMessages()).hasSize(21).contains("error19").doesNotContain("error20");
        assertThat(filteredLog.getInfoMessages()).hasSize(25).contains("info0").contains("info24");
    }

    private FilteredLog createLogWith20Elements() {
        FilteredLog filteredLog = new FilteredLog(TITLE);

        for (int i = 0; i < 25; i++) {
            filteredLog.logError("error%d", i);
            filteredLog.logInfo("info%d", i);
        }
        return filteredLog;
    }

    @Override
    protected FilteredLog createSerializable() {
        return createLogWith20Elements();
    }

    /** Actually tests {@link edu.hm.hafner.util.SerializableTest}. */
    @Test
    void shouldManuallyUseSerializationHelpers() {
        FilteredLog serializable = createSerializable();

        byte[] bytes = toByteArray(serializable);
        FilteredLog restored = restore(bytes);

        assertThat(restored).isEqualTo(serializable);
    }

    @Test
    void shouldObeyEqualsContract() {
        EqualsVerifier.simple().forClass(FilteredLog.class).verify();
    }
}
