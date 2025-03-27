package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.assertj.core.api.Assertions.*;

class OptionComparableTest extends AbstractComparableTest<Option> {
    /**
     * Creates a subject under test. The SUT must be smaller than the SUT of the opposite method
     * {@link #createGreaterSut()}.
     *
     * @return the SUT
     */
    @Override
    protected Option createSmallerSut() {
        return new Option("key-1", "value");
    }

    /**
     * Creates a subject under test. The SUT must be greater than the SUT of the opposite method
     * {@link #createSmallerSut()}.
     *
     * @return the SUT
     */
    @Override
    protected Option createGreaterSut() {
        return new Option("key-2", "value");
    }

    @Test
    void shouldThrowNpe() {
        assertThatNullPointerException().isThrownBy(
                () -> createSmallerSut().compareTo(null));
        assertThatNullPointerException().isThrownBy(
                () -> createGreaterSut().compareTo(null));
    }

    @Test
    void shouldAdhereToEquals() {
        EqualsVerifier.forClass(Option.class).withIgnoredFields("wert").verify();
    }
}
