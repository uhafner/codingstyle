package edu.hm.hafner.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link Set}.
 *
 * @author Eva Baumann
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
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testConstructorCapacityLoadFactor() {
        assertThatThrownBy(() -> new HashSet<Integer>(-20, (float) 0.75))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(20, (float) -0.75))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testConstructorCollection() {
        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldAdd() {
        HashSet emptyHs = createEmptyHashSet();

        assertThat(emptyHs.add(null)).isTrue();
        assertThat(emptyHs.add(-1)).isTrue();
        assertThat(emptyHs.add(1)).isTrue();
        assertThat(emptyHs.add(1)).isFalse();

    }

    @Test
    void shouldReturnSize() {
        HashSet filledHs = createFilledHashSet(1, 2, 5);

        assertThat(filledHs.size()).isEqualTo(3);
    }

    @Test
    void shouldBeEmpty() {
        HashSet emptyHs = createEmptyHashSet();
        HashSet filledHs = createFilledHashSet(1, 2, 5);

        assertThat(emptyHs.isEmpty()).isTrue();
        assertThat(filledHs.isEmpty()).isFalse();
    }

    @Test
    void shouldContains() {
        HashSet filledHs = createFilledHashSet(1, 2, 5);

        assertThat(filledHs.contains(2)).isTrue();
        assertThat(filledHs.contains(null)).isFalse();
    }

    @Test
    void shouldRemove() {
        HashSet filledHs = createFilledHashSet(1, 2, 5);

        assertThat(filledHs.remove(5)).isTrue();
        assertThat(filledHs.remove(5)).isFalse();
    }

    @Test
    void shouldClear() {
        HashSet filledHs = createFilledHashSet(1, 2, 4);

        assertThat(filledHs).isNotEmpty();

        filledHs.clear();

        assertThat(filledHs).isEmpty();
    }

    @Test
    void shouldClone() {
        HashSet filledHs = createFilledHashSet(1, 2, 5);

        assertThat(filledHs).isNotEmpty();

        Object o = filledHs.clone();

        assertThat(o).isEqualTo(filledHs);
    }

    @Test
    void shouldIterate() {
        HashSet filledHs = createFilledHashSet(1, 2, 5);
        Iterator<Integer> i = filledHs.iterator();

        assertThat(i.next()).isEqualTo(1);
        assertThat(i.next()).isEqualTo(2);
        assertThat(i.next()).isEqualTo(5);
        assertThat(i.hasNext()).isFalse();
    }
}
