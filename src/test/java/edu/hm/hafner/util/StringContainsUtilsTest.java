package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import static edu.hm.hafner.util.StringContainsUtils.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link StringContainsUtils}.
 *
 * @author Ullrich Hafner
 */
class StringContainsUtilsTest {
    @Test
    void shouldHandleNull() {
        assertThat(containsAnyIgnoreCase("This is a string text.", (String[]) null)).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.", (String) null)).isFalse();
        assertThat(containsAnyIgnoreCase("This is a string text.")).isFalse();

        assertThat(containsAnyIgnoreCase(null)).isFalse();
        assertThat(containsAnyIgnoreCase(null, (String) null)).isFalse();
        assertThat(containsAnyIgnoreCase(null, (String[]) null)).isFalse();

        assertThat(containsAllIgnoreCase("This is a string text.", (String[]) null)).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.", (String) null)).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.")).isFalse();

        assertThat(containsAllIgnoreCase(null)).isFalse();
        assertThat(containsAllIgnoreCase(null, (String) null)).isFalse();
        assertThat(containsAllIgnoreCase(null, (String[]) null)).isFalse();
    }

    @Test
    void shouldSearchStrings() {
        assertThat(containsAnyIgnoreCase("This is a string text.", "something")).isFalse();

        assertThat(containsAnyIgnoreCase("This is a string text.", "This")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "this")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "is")).isTrue();
        assertThat(containsAnyIgnoreCase("This is a string text.", "wrong", "IS")).isTrue();

        assertThat(containsAllIgnoreCase("This is a string text.", "something")).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.", "wrong", "is")).isFalse();
        assertThat(containsAllIgnoreCase("This is a string text.", "text", "with")).isFalse();

        assertThat(containsAllIgnoreCase("This is a string text.", "This")).isTrue();
        assertThat(containsAllIgnoreCase("This is a string text.", "this")).isTrue();
        assertThat(containsAllIgnoreCase("This is a string text.", "a", "is")).isTrue();
        assertThat(containsAllIgnoreCase("This is a string text.", "A", "iS")).isTrue();
        assertThat(containsAllIgnoreCase("This is a string text.", "a", "is", "string")).isTrue();
    }
}