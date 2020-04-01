package edu.hm.hafner.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link HashSet}.
 *
 * @author Dominik Moelter
 */
public class SetTest {

    HashSet createEmptyHashSet() {
        return new HashSet<Integer>();
    }

    HashSet createFilledHashSet(final Integer... values) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        Collections.addAll(hashSet, values);
        return hashSet;
    }

    @Test
    void testConstructorCapacity() {
        assertThatThrownBy(() -> new HashSet<Integer>(-1))
                .as("Negative capacity throws IllegalArgumentException")
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testConstructorCapacityLoadFactor() {
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThatThrownBy(() -> new HashSet<Integer>(-16, (float) 0.75))
                .as("Negative capacity throws IllegalArgumentException")
                .isExactlyInstanceOf(IllegalArgumentException.class);
        softAssertions.assertThatThrownBy(() -> new HashSet<Integer>(16, (float) -0.75))
                .as("Negative capacity throws IllegalArgumentException")
                .isExactlyInstanceOf(IllegalArgumentException.class);

        softAssertions.assertAll();
    }

    @Test
    void testConstructorCollection() {
        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .as("Null collection throws NullPointerException")
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldAdd() {
        HashSet h = createEmptyHashSet();
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(h.add(null))
                .as("Add Null")
                .isTrue();
        softAssertions.assertThat(h.add(-1))
                .as("Add minus one")
                .isTrue();
        softAssertions.assertThat(h.add(0))
                .as("Add zero")
                .isTrue();
        softAssertions.assertThat(h.add(1))
                .as("Add one")
                .isTrue();
        softAssertions.assertThat(h.add(1))
                .as("Add another one")
                .isFalse();

        softAssertions.assertAll();
    }

    @Test
    void shouldRemove() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(h.remove(2))
                .as("Removing contained value")
                .isTrue();
        softAssertions.assertThat(h.remove(2))
                .as("Removing not contained value")
                .isFalse();

        softAssertions.assertAll();
    }

    @Test
    void shouldClear() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3, 4);
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(h)
                .as("HashSet filled")
                .isNotEmpty();

        h.clear();
        softAssertions.assertThat(h)
                .as("HashSet cleared")
                .isEmpty();

        softAssertions.assertAll();
    }

    @Test
    void shouldClone() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);
        Object o = h.clone();
        assertThat(o).as("cloned object").isEqualTo(h);
    }

    @Test
    void shouldContains() {
        HashSet<Integer> h = createFilledHashSet(1);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(h.contains(1))
                .as("Contained value")
                .isTrue();
        softAssertions.assertThat(h.contains(2))
                .as("Not contained value")
                .isFalse();

        softAssertions.assertAll();
    }

    @Test
    void shouldBeEmpty() {
        HashSet<Integer> h = createEmptyHashSet();

        assertThat(h.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnSize() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(h.size())
                .as("Size of filled HashSet")
                .isEqualTo(3);

        h.clear();

        softAssertions.assertThat(h.size())
                .as("Size of empty HashSet")
                .isEqualTo(0);

        softAssertions.assertAll();
    }

    @Test
    void shouldIterate() {
        HashSet<Integer> h = createFilledHashSet(1, 2);
        Iterator<Integer> i = h.iterator();
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(i.next())
                .as("First Iteration")
                .isEqualTo(1);
        softAssertions.assertThat(i.next())
                .as("Second Iteration")
                .isEqualTo(2);
        softAssertions.assertThat(i.hasNext())
                .as("Has no more values")
                .isFalse();

        softAssertions.assertAll();
    }
}
