package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.util.ChangeEvent.ChangeType;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link ChangeEvent}.
 *
 * @author Ullrich Hafner
 */
class ChangeEventTest {
    @Test
    void shouldAdhereToEquals() {
        EqualsVerifier.forClass(ChangeEvent.class).verify();
    }

    @Test
    void shouldCorrectlyCreateEvent() {
        ChangeEvent<Integer> event = new ChangeEvent<>(10, ChangeType.ADDITION);

        assertThat(event.getType()).isEqualTo(ChangeType.ADDITION);
        assertThat(event.getElement()).isEqualTo(10);
        assertThat(event).hasToString("10 (ADDITION)");
    }
}
