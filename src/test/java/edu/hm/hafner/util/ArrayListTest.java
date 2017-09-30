package edu.hm.hafner.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the class {@link ArrayList}.
 *
 * @author Ullrich Hafner
 */
public class ArrayListTest {
    /** Verifies that a new list is empty. */
    @Test
    public void shouldBeEmptyWhenCreatedShortForm() {
        List<String> strings = new ArrayList<>();

        assertEquals("List is not empty", 0, strings.size());
        assertTrue("List is not empty", strings.isEmpty());
    }

    /** Verifies that an element can be added and retrieved. */
    @Test
    public void shouldReturnStoredElement() {
        List<String> strings = new ArrayList<>();
        String element = "Hello World!";
        strings.add(element);

        assertEquals("Wrong list size", 1, strings.size());
        assertFalse("List is empty", strings.isEmpty());
        assertEquals("Wrong first element in list", element, strings.get(0));
    }

    /**
     * Verifies that an {@link IndexOutOfBoundsException} is thrown if the requested index is not in the list.
     */
    @Test(expected = IndexOutOfBoundsException.class) // Then
    public void shouldThrowExceptionIfIndexIsTooLarge() {
        // Given
        List<String> strings = new ArrayList<>();
        // When
        strings.get(0);
    }
}
