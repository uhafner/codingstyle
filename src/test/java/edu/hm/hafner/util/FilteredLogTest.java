package edu.hm.hafner.util;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

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
        var filteredLog = new FilteredLog(TITLE, 5);

        assertThat(filteredLog).hasNoErrorMessages().hasNoInfoMessages();
        assertThat(filteredLog.size()).isEqualTo(0);

        var empty = new FilteredLog();
        assertThat(empty.size()).isEqualTo(0);
    }

    @Test
    void shouldLogAllErrors() {
        var filteredLog = new FilteredLog(TITLE, 5);

        assertThat(filteredLog).doesNotHaveErrors();
        filteredLog.logError("1");
        assertThat(filteredLog).hasErrors();

        filteredLog.logError("2");
        filteredLog.logError("3");
        filteredLog.logError("4");
        filteredLog.logError("5");

        assertThatExactly5MessagesAreLogged(filteredLog);
        assertThat(filteredLog.size()).isEqualTo(5);
    }

    @Test
    void shouldSkipAdditionalErrors() {
        FilteredLog filteredLog = create5ErrorsLogWithTitle(StringUtils.EMPTY);

        verifyFiveErrorMessages(filteredLog);
    }

    @Test
    void shouldSkipAdditionalErrorsWithTitle() {
        FilteredLog filteredLog = create5ErrorsLogWithTitle(TITLE);

        assertThat(filteredLog).hasErrorMessages(TITLE);

        verifyFiveErrorMessages(filteredLog);
    }

    private void verifyFiveErrorMessages(final FilteredLog filteredLog) {
        assertThat(filteredLog).hasErrorMessages(
                "1", "2", "3", "4", "5",
                "java.lang.IllegalStateException: 1",
                "java.lang.IllegalStateException: 2",
                "java.lang.IllegalStateException: 3",
                "java.lang.IllegalStateException: 4",
                "java.lang.IllegalStateException: 5",
                "  ... skipped logging of 2 additional errors ...");

        assertThat(filteredLog).doesNotHaveErrorMessages(
                "6",
                "java.lang.IllegalStateException: 6",
                "7",
                "java.lang.IllegalStateException: 7");
    }

    private FilteredLog create5ErrorsLogWithTitle(final String title) {
        var filteredLog = new FilteredLog(title, 5);

        for (int i = 1; i < 8; i++) {
            filteredLog.logException(new IllegalStateException(String.valueOf(i)), "%d", i);
        }

        assertThat(filteredLog.size()).isEqualTo(7);

        return filteredLog;
    }

    private void assertThatExactly5MessagesAreLogged(final FilteredLog filteredLog) {
        assertThat(filteredLog).hasOnlyErrorMessages(TITLE, "1", "2", "3", "4", "5");
    }

    @Test
    void shouldMergeLogger() {
        var parent = new FilteredLog("Parent Errors");

        parent.logInfo("parent Info 1");
        parent.logError("parent Error 1");

        var child = new FilteredLog("Child Errors");
        child.logInfo("child Info 1");
        child.logError("child Error 1");

        parent.merge(child);

        assertThat(parent).hasOnlyInfoMessages("parent Info 1", "child Info 1")
                .hasOnlyErrorMessages("Parent Errors", "parent Error 1", "Child Errors", "child Error 1");
        assertThat(parent.size()).isEqualTo(1);
    }

    @Test
    void shouldSkipEmptyErrorLogWhenMerging() {
        var parent = new FilteredLog("Parent Errors");

        parent.logInfo("parent Info 1");

        var child = new FilteredLog("Child Errors");
        child.logInfo("child Info 1");
        child.logError("child Error 1");

        parent.merge(child);

        assertThat(parent).hasOnlyInfoMessages("parent Info 1", "child Info 1")
                .hasOnlyErrorMessages("Child Errors", "child Error 1");
        assertThat(parent.size()).isZero();
    }

    @Test
    void shouldLogExceptions() {
        var filteredLog = new FilteredLog(TITLE, 1);

        filteredLog.logException(new IllegalArgumentException("Cause"), "Message");
        filteredLog.logException(new IllegalArgumentException(""), "Message");

        assertThat(filteredLog).hasErrorMessages(TITLE,
                "Message", "java.lang.IllegalArgumentException: Cause");
    }

    @Test
    void shouldLog20ErrorsByDefault() {
        FilteredLog filteredLog = createLogWith20Elements();

        assertThat(filteredLog.getErrorMessages()).hasSize(22)
                .contains(TITLE)
                .contains("error19")
                .doesNotContain("error20")
                .contains("  ... skipped logging of 5 additional errors ...");
        assertThat(filteredLog.getInfoMessages()).hasSize(25)
                .contains("info0")
                .contains("info24");
    }

    @Test
    void shouldAdhereToEquals() {
        EqualsVerifier.forClass(FilteredLog.class)
                .withIgnoredFields("lock")
                .withPrefabValues(ReentrantLock.class, new ReentrantLock(), new ReentrantLock())
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Override
    protected void assertThatRestoredInstanceEqualsOriginalInstance(final FilteredLog original,
            final FilteredLog restored) {
        assertThat(original).usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("lock").build()).isEqualTo(restored);
    }

    private FilteredLog createLogWith20Elements() {
        var filteredLog = new FilteredLog(TITLE);

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

        assertThatRestoredInstanceEqualsOriginalInstance(serializable, restored);
    }
}
