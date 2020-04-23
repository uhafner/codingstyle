package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link StringUtils}.
 *
 * @author Your Name
 */
class StringUtilsTest {
    @Test
    void shouldCoverAllPathsOfIsEmpty() {
        assertThat(StringUtils.isEmpty("")).isTrue();
        assertThat(StringUtils.isEmpty(null)).isTrue();
        assertThat(StringUtils.isEmpty(null)).isTrue();
    }
}
