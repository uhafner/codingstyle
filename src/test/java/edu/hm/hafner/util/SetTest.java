package edu.hm.hafner.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link HashSet}.
 *
 * @author Edward Gangkofer
 */
public class SetTest {

    private HashSet<Integer> createEmpty() {
        return new HashSet<>();
    }

    private HashSet<Integer> createFilled(int... numbers) {
        HashSet<Integer> filled = new HashSet<>(numbers.length);
        for (int i : numbers) {
            filled.add(i);
        }
        return filled;
    }

    @Test
    void shouldHandleWrongConstructorParameters() {

        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(-1, 13))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(6, (float) -0.8))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(8, (float) -0.25))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new HashSet<Integer>(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAdd() {
        HashSet<Integer> test = createEmpty();
        assertThat(test.add(0)).isTrue();
        assertThat(test.add(0)).isFalse();
        assertThat(test.add(-5)).isTrue();
        assertThat(test.add(null)).isTrue();
        assertThat(test.add(null)).isFalse();
    }

    @Test
    void shouldRemove() {
        HashSet<Integer> test = createFilled(1, 2, 3, 4, 5);
        assertThat(test.remove(1)).isTrue();
        assertThat(test.remove(1)).isFalse();
        assertThat(test.remove(0)).isFalse();
        assertThat(test.remove(null)).isFalse();
    }

    @Test
    void shouldClear() {
        HashSet<Integer> testHashSet = createFilled(1, 2, 3);
        assertThat(testHashSet).isNotEmpty();
        testHashSet.clear();
        assertThat(testHashSet).isEmpty();
    }

    @Test
    void shouldClone() {
        HashSet<Integer> testFilled = createFilled(0, 1, 2, 3);
        HashSet<Integer> testEmpty = createEmpty();
        assertThat(testEmpty).isEmpty();
        testEmpty = (HashSet<Integer>) testFilled.clone();
        assertThat(testEmpty).isNotEmpty();
        assertThat(testEmpty).isEqualTo(testFilled);
    }

    @Test
    void shouldContain() {
        HashSet<Integer> testFilled = createFilled(0, 1, 2, 3);
        assertThat(testFilled.contains(0)).isTrue();
        assertThat(testFilled.contains(-1)).isFalse();
        assertThat(testFilled.contains(null)).isFalse();
    }

    @Test
    void shouldBeEmpty() {
        HashSet<Integer> testFilled = createFilled(0, 1, 2, 3);
        HashSet<Integer> testEmpty = createEmpty();

        assertThat(testEmpty).isEmpty();
        testEmpty.add(null);
        assertThat(testEmpty).isNotEmpty();

        assertThat(testFilled).isNotEmpty();
        testFilled.clear();
        assertThat(testFilled).isEmpty();
    }

    @Test
    void shouldIterate() {
        HashSet<Integer> testFilled = createFilled(0, 1);
        Iterator<Integer> testIterator = testFilled.iterator();
        assertThat(testIterator.next()).isEqualTo(0);
        assertThat(testIterator.next()).isEqualTo(1);
        assertThat(testIterator.hasNext()).isFalse();
    }

    @Test
    void shouldReturnSize() {
        HashSet<Integer> testFilled = createFilled(0, 1);
        HashSet<Integer> testEmpty = createEmpty();
        assertThat(testEmpty.size()).isEqualTo(0);
        assertThat(testFilled.size()).isEqualTo(2);
        testFilled.remove(1);
        assertThat(testFilled.size()).isEqualTo(1);
    }
}
