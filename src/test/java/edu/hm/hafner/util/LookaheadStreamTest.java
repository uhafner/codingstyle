package edu.hm.hafner.util;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static edu.hm.hafner.util.assertions.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link LookaheadStream}.
 *
 * @author Ullrich Hafner
 */
class LookaheadStreamTest extends ResourceTest {
    private static final String FIRST_LINE = "First Line";
    private static final String EMPTY = StringUtils.EMPTY;

    @Test
    void shouldHandleEmptyLines() {
        try (var stream = new LookaheadStream(getTextLinesAsStream(""))) {
            assertThat(stream).doesNotHaveNext().hasLine(0).hasFileName(EMPTY);

            assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(stream::peekNext);
            assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(stream::next);
        }
    }

    @Test
    void shouldReturnSingleLine() {
        try (var stream = new LookaheadStream(getTextLinesAsStream(FIRST_LINE))) {
            assertThat(stream).hasNext().hasLine(0);
            assertThat(stream.peekNext()).isEqualTo(FIRST_LINE);
            // Now reading from the buffer:
            assertThat(stream).hasNext();
            assertThat(stream.peekNext()).isEqualTo(FIRST_LINE);

            assertThat(stream.next()).isEqualTo(FIRST_LINE);
            assertThat(stream).hasLine(1).doesNotHaveNext();
        }
    }

    @Test
    void shouldReturnMultipleLines() {
        try (var stream = new LookaheadStream(getTextLinesAsStream("First Line\nSecond Line"))) {
            assertThat(stream.hasNext()).isTrue();
            assertThat(stream.next()).isEqualTo(FIRST_LINE);
            assertThat(stream.getLine()).isEqualTo(1);
            assertThat(stream.hasNext()).isTrue();
            assertThat(stream.next()).isEqualTo("Second Line");
            assertThat(stream.getLine()).isEqualTo(2);

            assertThat(stream.hasNext()).isFalse();
        }
    }

    @Test
    void shouldReturnLookAheadLines() {
        try (var stream = new LookaheadStream(getTextLinesAsStream("First Line\nSecond Line"))) {
            assertThat(stream.hasNext()).isTrue();
            assertThat(stream.hasNext("Line$")).isTrue();
            assertThat(stream.hasNext("Second.*")).isFalse();
            assertThat(stream.next()).isEqualTo(FIRST_LINE);
            assertThat(stream.getLine()).isEqualTo(1);

            assertThat(stream.hasNext()).isTrue();
            assertThat(stream.hasNext("Line$")).isTrue();
            assertThat(stream.hasNext("First.*")).isFalse();
            assertThat(stream.next()).isEqualTo("Second Line");
            assertThat(stream.getLine()).isEqualTo(2);

            assertThat(stream.hasNext()).isFalse();
            assertThat(stream.hasNext(".*")).isFalse();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCloseStream() {
        try (Stream<String> lines = mock(Stream.class)) {
            try (var stream = new LookaheadStream(lines)) {
                assertThat(stream.getLine()).isZero();
            }

            verify(lines).close(); // lines will be closed by stream
        }
    }
}
