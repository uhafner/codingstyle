package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static edu.hm.hafner.util.assertions.Assertions.*;

/**
 * Tests the class {@link LineRangeList}.
 *
 * @author Kohsuke Kawaguchi
 * @author Ullrich Hafner
 */
class LineRangeTest {
    @Test
    void shouldRefuseIllegalValue() {
        assertThat(new LineRange(-5, 1)).hasStart(0).hasEnd(0).isSingleLine();
    }

    @Test
    void shouldFindLinesInsideAndOutsideOfLineRange() {
        var lineRange = new LineRange(1, 2);

        assertThat(lineRange.contains(0)).isFalse();
        assertThat(lineRange.contains(1)).isTrue();
        assertThat(lineRange.contains(2)).isTrue();
        assertThat(lineRange.contains(3)).isFalse();
        assertThat(lineRange).hasStart(1).hasEnd(2)
                .hasLines(1, 2).isNotSingleLine().hasToString("[1-2]");

        var wrongOrder = new LineRange(2, 1);
        assertThat(wrongOrder.contains(0)).isFalse();
        assertThat(wrongOrder.contains(1)).isTrue();
        assertThat(wrongOrder.contains(2)).isTrue();
        assertThat(wrongOrder.contains(3)).isFalse();
        assertThat(wrongOrder).hasStart(1).hasEnd(2)
                .hasLines(1, 2).isNotSingleLine().hasToString("[1-2]");

        var point = new LineRange(2);
        assertThat(point.contains(1)).isFalse();
        assertThat(point.contains(2)).isTrue();
        assertThat(point.contains(3)).isFalse();
        assertThat(point).hasStart(2).hasEnd(2)
                .hasLines(2).isSingleLine().hasToString("[2-2]");
    }

    @Test
    void shouldAdhereToEquals() {
        EqualsVerifier.forClass(LineRange.class).verify();
    }
}
