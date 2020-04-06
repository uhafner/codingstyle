package edu.hm.hafner.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests methods of the class {@link HashSet}.
 *
 * @author Sandra Zunabovic
 */
public class SetTest {

    HashSet<Integer> createEmptyHashSet() {
        return new HashSet();
    }

    HashSet<Integer> createFilledHashSet(final Integer... addedValues) {
        HashSet<Integer> hashSet = new HashSet<>();
        Collections.addAll(hashSet, addedValues);
        return hashSet;
    }

    @Test
    void testConstructorCapacity() {
        assertThatThrownBy(() -> new HashSet<Integer>(-1))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testConstructorCapacityLoadFactor() {
        assertThatThrownBy(() -> new HashSet<Integer>(-5, (float) 0.75))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(5, (float) -0.75))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testConstructorCollection() {
        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldIterate() {
        HashSet<Integer> h = createFilledHashSet(1, 2);
        Iterator<Integer> i = h.iterator();

        assertThat(i.next()).as("First Iteration").isEqualTo(1);
        assertThat(i.next()).as("Second Iteration").isEqualTo(2);
        assertThat(i.hasNext()).as("No more values available").isFalse();
    }

    @Test
    void shouldReturnSize() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);

        assertThat(h.size())
                .as("Size of filled HashSet")
                .isEqualTo(3);

        h.clear();

        assertThat(h.size())
                .as("Size of empty HashSet")
                .isEqualTo(0);
    }

    @Test
    void shouldBeEmpty() {
        HashSet<Integer> h = createEmptyHashSet();
        assertThat(h.size()).isEqualTo(0);
    }

    @Test
    void shouldContain() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);

        assertThat(h.contains(1)).as("Contained value").isTrue();
        assertThat(h.contains(10)).as("Value not contained").isFalse();
    }

    @Test
    void shouldAdd() {
        HashSet<Integer> h = createEmptyHashSet();

        assertThat(h.add(1)).as("1 added").isTrue();
        assertThat(h.add(1)).as("Another 1 added").isFalse();
        assertThat(h.contains(1)).isTrue();

        assertThat(h.add(-1)).as("-1 added").isTrue();
        assertThat(h.contains(-1)).isTrue();

        assertThat(h.add(null)).as("Null added").isTrue();
        assertThat(h.contains(null)).isTrue();

        assertThat(h.add(0)).as("0 added").isTrue();
        assertThat(h.contains(0)).isTrue();
    }

    @Test
    void shouldRemove() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);
        assertThat(h.contains(1)).isTrue();
        assertThat(h.remove(1)).as("Remove value included").isTrue();
        assertThat(h.contains(1)).isFalse();

        assertThat(h.contains(0)).isFalse();
        assertThat(h.remove(0)).as("Remove value not included").isFalse();
        assertThat(h.contains(0)).isFalse();
    }

    @Test
    void shouldClear() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);
        assertThat(h.isEmpty()).isFalse();
        h.clear();
        assertThat(h.isEmpty()).isTrue();
    }

    @Test
    void shouldClone() {
        HashSet<Integer> h = createFilledHashSet(1, 2, 3);
        Object g = h.clone();
        assertThat(h.clone()).isEqualTo(g);
    }

}
