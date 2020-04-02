package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;


/**
 * Tests the class {@link HashSet}.
 *
 * @author Maximilian Arnold
 */
public class SetTest {

    @Test
    void testConstructor() {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> set1 = new HashSet<>(16);
        HashSet<Integer> set2 = new HashSet<> (16, 0.75f);

        assertThat(set).isEqualTo(set1);
        assertThat(set).isEqualTo(set2);
        assertThat(set1).isEqualTo(set2);
    }

    @Test
    void testIterator() {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> empty = new HashSet<>();

        set.add(1);
        set.add(2);
        set.add(3);

        Iterator<Integer> iterator = set.iterator();

        for (int i = 1; iterator.hasNext(); i++) {
            assertThat(iterator.next()).isEqualTo(i);
        }

        assertThat(iterator.hasNext()).isFalse();

        Iterator<Integer> emptyIterator = empty.iterator();

        assertThat(emptyIterator.hasNext()).isFalse();
    }

    @Test
    void testSize() {
        HashSet<Integer> set = new HashSet<>();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.size()).isEqualTo(3);
        assertThat(set.size()).isNotEqualTo(4);
        assertThat(set.size()).isNotNull();
    }

    @Test
    void testIsEmpty() {
        HashSet<Integer> set = new HashSet<>();

        set.add(1);

        assertThat(set.isEmpty()).isFalse();

        set.clear();

        assertThat(set.isEmpty()).isTrue();
    }

    @Test
    void testContainsAndAdd() {
        HashSet<Integer> set = new HashSet<>();

        set.add(1);

        assertThat(set.contains(1)).isTrue();
        assertThat(set.contains(3)).isFalse();
        assertThat(set.contains(null)).isFalse();
    }

    @Test
    void testRemove() {
        HashSet<Integer> set = new HashSet<>();

        set.add(1);
        set.add(2);
        set.remove(1);

        assertThat(set.contains(1)).isFalse();
        assertThat(set.contains(2)).isTrue();
        assertThat(set.contains(3)).isFalse();
        assertThat(set.remove(3)).isFalse();
    }

    @Test
    void testClear() {
        HashSet<Integer> set = new HashSet<>();

        set.add(1);
        set.clear();

        assertThat(set).isEmpty();
        assertThat(set.contains(1)).isFalse();
    }

    @Test
    void testClone() {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> empty;

        set.add(1);
        set.add(2);

        assertThat(set).isEqualTo(set.clone());

        empty = (HashSet<Integer>) set.clone();

        assertThat(empty).isEqualTo(set);
    }
}
