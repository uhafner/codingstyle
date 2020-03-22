package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import static edu.hm.hafner.util.StringContainsUtils.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link StringContainsUtils}.
 *
 * @author Ullrich Hafner
 * @author Ludwig Karpfinger
 *
 */
class StringContainsUtilsTest {

    @Test
    void shouldHandleNull() {
        // ANY
        assertThat(containsAnyIgnoreCase("This is a string text.", (String[]) null)).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.", (String) null)).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.")).isFalse();

        assertThat(containsAnyIgnoreCase(null)).isFalse();
        assertThat(containsAnyIgnoreCase(null, (String) null)).isFalse();
        assertThat(containsAnyIgnoreCase(null, (String[]) null)).isFalse();

        // ALL
        assertThat(containsAllIgnoreCase("This is a string text.", (String[]) null)).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.", (String) null)).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.")).isFalse();

        assertThat(containsAllIgnoreCase(null)).isFalse();
        assertThat(containsAllIgnoreCase(null, (String) null)).isFalse();
        assertThat(containsAllIgnoreCase(null, (String[]) null)).isFalse();
    }

    @Test
    void shouldSearchStrings() {
        // ANY
        assertThat(containsAnyIgnoreCase("This is a string text.", "something")).isFalse();

        assertThat(containsAnyIgnoreCase("This is a string text.", "This")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "this")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "is")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "IS")).isTrue();

        // ALL
        assertThat(containsAnyIgnoreCase("This is a string text.", "something")).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "is")).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "IS")).isFalse();

        assertThat(containsAnyIgnoreCase("This is a string text.", "This")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "this")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "sTring", "IS")).isTrue();
    }
}
