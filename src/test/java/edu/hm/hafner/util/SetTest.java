package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class testHashSet.
 *
 * @author Daniel Richter
 */
public class SetTest {
    HashSet<Integer> createEmptyIntegerHashSet() {
        return new HashSet<>();
    }

    HashSet<Integer> createFilledIntegerHashSet(final Integer... payload) {
        return new HashSet<>(asList(payload));
    }

    @Test
    void shouldHandleWrongParameterInConstructor() {
        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(-1, 2))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(2, (float) -0.5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(2, (float) -0.5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(-5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAdd() {
        HashSet<Integer> testHashSet = createEmptyIntegerHashSet();

        assertThat(testHashSet.add(1)).isTrue();
        assertThat(testHashSet.add(1)).isFalse();
        assertThat(testHashSet.add(0)).isTrue();
        assertThat(testHashSet.add(-1)).isTrue();
        assertThat(testHashSet.add(null)).isTrue();
        assertThat(testHashSet.add(null)).isFalse();
    }

    @Test
    void shouldClear() {
        HashSet<Integer> testHashSet = createFilledIntegerHashSet(1, 2, 3);

        assertThat(testHashSet).isNotEmpty();
        testHashSet.clear();
        assertThat(testHashSet).isEmpty();
    }

    @Test
    void shouldClone() {
        HashSet<Integer> testFilledtestHashSet = createFilledIntegerHashSet(1, 2, 3);
        HashSet<Integer> testEmptytestHashSet = createEmptyIntegerHashSet();

        assertThat(testFilledtestHashSet).isEqualTo(testFilledtestHashSet.clone());
        assertThat(testEmptytestHashSet).isEqualTo(testEmptytestHashSet.clone());
    }

    @Test
    void shouldContain() {
        HashSet<Integer> filledtestHashSet = createFilledIntegerHashSet(1, 2, 3);
        HashSet<Integer> emptytestHashSet = createEmptyIntegerHashSet();

        assertThat(filledtestHashSet.contains(1)).isTrue();
        assertThat(filledtestHashSet.contains(4)).isFalse();
        assertThat(emptytestHashSet.contains(null)).isFalse();
        assertThat(emptytestHashSet.contains(0)).isFalse();
        assertThat(emptytestHashSet.contains(-1)).isFalse();
    }

    @Test
    void shouldBeEmpty() {
        HashSet<Integer> filledtestHashSet = createFilledIntegerHashSet(1, 2, 3);
        HashSet<Integer> emptytestHashSet = createEmptyIntegerHashSet();

        assertThat(filledtestHashSet.isEmpty()).isFalse();
        assertThat(emptytestHashSet.isEmpty()).isTrue();
        emptytestHashSet.add(null);
        assertThat(emptytestHashSet.isEmpty()).isFalse();
    }

    @Test
    void shouldIterate() {
        HashSet<Integer> filledtestHashSet = createFilledIntegerHashSet(1, 2, 3);
        Iterator<Integer> testIterator = filledtestHashSet.iterator();

        assertThat(testIterator.next()).isEqualTo(1);
        assertThat(testIterator.next()).isEqualTo(2);
        assertThat(testIterator.next()).isEqualTo(3);
        assertThat(testIterator.hasNext()).isFalse();
    }

    @Test
    void shouldRemove() {
        HashSet<Integer> filledtestHashSet = createFilledIntegerHashSet(1, 2, 3);

        assertThat(filledtestHashSet.contains(1)).isTrue();
        assertThat(filledtestHashSet.remove(1)).isTrue();
        assertThat(filledtestHashSet.contains(1)).isFalse();
        assertThat(filledtestHashSet.remove(1)).isFalse();
        assertThat(filledtestHashSet.contains(null)).isFalse();
        assertThat(filledtestHashSet.remove(null)).isFalse();
    }

    @Test
    void shouldReturnSize() {
        HashSet<Integer> filledHashSet = createFilledIntegerHashSet(1, 2, 3);

        assertThat(filledHashSet.size()).isEqualTo(3);
        filledHashSet.remove(1);
        assertThat(filledHashSet.size()).isEqualTo(2);
        filledHashSet.add(null);
        assertThat(filledHashSet.size()).isEqualTo(3);
    }
}
