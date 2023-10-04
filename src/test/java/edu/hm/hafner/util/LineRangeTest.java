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
    void shouldCreateSingleLine() {
        assertThat(new LineRange(5, 5)).hasStart(5).hasEnd(5).isSingleLine();
        assertThat(new LineRange(5)).hasStart(5).hasEnd(5).isSingleLine();

        assertThat(new LineRange(5, 4)).hasStart(4).hasEnd(5).isNotSingleLine();
    }

    @Test
    void shouldObeyEqualsContract() {
        EqualsVerifier.simple().forClass(LineRange.class).verify();
    }
}
