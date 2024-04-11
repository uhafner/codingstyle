package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link Option}.
 *
 * @author Ullrich Hafner
 */
class OptionFullTest extends SerializableTest<Option> {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    void shouldCreateOption() {
        Option option = createObject();

        assertThat(option.getKey()).isEqualTo(KEY);
        assertThat(option.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldEnsureEqualsContracts() {
        Option option = createObject();

        assertThat(option.equals(option)).isTrue();
        assertThat(option.equals(null)).isFalse();
    }

    @Test
    void shouldEnsureEqualsContractsAbstractTest() {
        Object object = createObject();

        assertThat(object.equals(object)).isTrue();
        assertThat(object.equals(null)).isFalse();
    }

    @Test
    void shouldEnsureEqualsWithEqualsVerifier() {
        EqualsVerifier.forClass(Option.class).verify();
    }

    private Option createObject() {
        return new Option(KEY, VALUE);
    }

    /**
     * Verifies that saved serialized format (from a previous release) still can be resolved with the current
     * implementation of {@link Option}.
     */
    @Test
    void shouldReadIssueFromOldSerialization() {
        byte[] restored = readAllBytes("/option.ser");

        assertThatSerializableCanBeRestoredFrom(restored);
    }

    @Override
    protected Option createSerializable() {
        return createObject();
    }
}
