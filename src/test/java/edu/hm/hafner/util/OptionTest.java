package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link Option}.
 *
 * @author Ullrich Hafner
 */
class OptionTest extends SerializableTest<Option> {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    void shouldCreateOption() {
        Option option = new Option(KEY, VALUE);

        assertThat(option.getKey()).isEqualTo(KEY);
        assertThat(option.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldVerifyEquals() {
        EqualsVerifier.forClass(Option.class).verify();
    }

    /**
     * Verifies that saved serialized format (from a previous release) still can be resolved with the current
     * implementation of {@link Option}.
     */
    @Test
    void shouldReadOldInstance() {
        byte[] restored = readAllBytes("/option.ser");

        assertThatSerializableCanBeRestoredFrom(restored);
    }

    @Override
    protected Option createSerializable() {
        return new Option(KEY, VALUE);
    }
}
