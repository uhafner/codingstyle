package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class testHashSet.
 *
 * @author Sherif El Manakhly
 */
public class SetTest {
    HashSet<Integer> createFilledHashSet(final Integer... content) {
        return new HashSet<>(asList(content));
    }

    HashSet<Integer> createEmptyHashSet() {
        return new HashSet<>();
    }

    @Test
    void shouldIterate() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        Iterator<Integer> filledTestIterator = filledTestHashSet.iterator();
        assertThat(filledTestIterator.next()).isEqualTo(0);
        assertThat(filledTestIterator.next()).isEqualTo(1);
        assertThat(filledTestIterator.next()).isEqualTo(2);
        assertThat(filledTestIterator.next()).isEqualTo(3);
        assertThat(filledTestIterator.hasNext()).isFalse();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        Iterator<Integer> emptyTestIterator = emptyTestHashSet.iterator();
        assertThat(emptyTestIterator.hasNext()).isFalse();
    }

    @Test
    void shouldBeSize() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet.size() == 4).isTrue();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet.size() == 0).isTrue();
    }

    @Test
    void shouldBeEmpty() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet.isEmpty()).isFalse();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet.isEmpty()).isTrue();
    }

    @Test
    void shouldContain() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet.contains(0)).isTrue();
        assertThat(filledTestHashSet.contains(5)).isFalse();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet.contains(null)).isFalse();
        assertThat(emptyTestHashSet.contains(2)).isFalse();
    }

    @Test
    void shouldAdd() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet.add(1)).isFalse();
        assertThat(filledTestHashSet.add(4)).isTrue();
        assertThat(filledTestHashSet.add(4)).isFalse();
        assertThat(filledTestHashSet.add(-1)).isTrue();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet.add(null)).isTrue();
        assertThat(emptyTestHashSet.add(null)).isFalse();
    }

    @Test
    void shouldRemove() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet.remove(0)).isTrue();
        assertThat(filledTestHashSet.remove(0)).isFalse();
        assertThat(filledTestHashSet.remove(null)).isFalse();

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet.remove(null)).isFalse();
    }

    @Test
    void shouldClear() {
        HashSet<Integer> testHashSet = createFilledHashSet(0, 1, 2, 3);
        testHashSet.clear();
        assertThat(testHashSet).isEmpty();
    }

    @Test
    void shouldClone() {
        HashSet<Integer> filledTestHashSet = createFilledHashSet(0, 1, 2, 3);
        assertThat(filledTestHashSet).isEqualTo(filledTestHashSet.clone());

        HashSet<Integer> emptyTestHashSet = createEmptyHashSet();
        assertThat(emptyTestHashSet).isEqualTo(emptyTestHashSet.clone());
    }
}