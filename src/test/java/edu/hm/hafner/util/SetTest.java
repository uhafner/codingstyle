package edu.hm.hafner.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.HashSet;

/**
 * Tests the class HashSet.
 *
 * @author Sofia Steinleitner
 */
class SetTest {
    private static final Integer eins = 1;
    private static final Integer zwei = 2;
    private static final Integer drei = 3;
    private static final Integer vier = 4;

    @Test
    void shouldTestConstructorHashSet() {
        HashSet<Integer> set = new HashSet<Integer>();
        assertThat(set).isEmpty();
    }

    @Test
    void shouldTestConstructorCollection() {
        assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isExactlyInstanceOf(NullPointerException.class)
                .as("If the specified collection is null, throws NullPointerException");
    }

    @Test
    void shouldTestConstructorCapacity() {
        assertThatThrownBy(() -> new HashSet<Integer>(-1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero, throws IllegalArgumentException");
    }

    @Test
    void shouldTestConstructorCapacityLoadFactor() {
        assertThatThrownBy(() -> new HashSet<Integer>(-1, -15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");

        assertThatThrownBy(() -> new HashSet<Integer>(1, -15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");

        assertThatThrownBy(() -> new HashSet<Integer>(-1, 15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");
    }

    @Test
    void shouldAddElements() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);

        assertThat(set.add(drei)).isTrue();
        assertThat(set.add(zwei)).isFalse();
        assertThat(set).contains(eins);
        assertThat(set).contains(zwei);
        assertThat(set).contains(drei);
    }

    @Test
    void shouldContainElements() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);

        Assertions.assertThat(set.contains(eins)).isTrue();
        Assertions.assertThat(set.contains(zwei)).isTrue();
        Assertions.assertThat(set.contains(vier)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEmpty() {
        HashSet<Integer> set = new HashSet<Integer>();
        assertThat(set.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEmpty() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        assertThat(set.isEmpty()).isFalse();
    }

    @Test
    void shouldRemoveElements() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        assertThat(set.remove(eins)).isTrue();
        assertThat(set.remove(eins)).isFalse();
        assertThat(set.contains(eins)).isFalse();
    }

    @Test
    void shouldClearAllElements() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        set.clear();
        assertThat(set).isEmpty();
    }

    @Test
    void shouldReturnCorrectSize() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        assertThat(set).hasSize(drei);
        set.remove(drei);
        assertThat(set.size()).isEqualTo(zwei);
        set.clear();
        assertThat(set.size()).isEqualTo(0);
    }

    @Test
    void shouldNotFindElementWhenEmpty() {
        HashSet<Integer> set = new HashSet<Integer>();
        Iterator<Integer> it = set.iterator();

        assertThat(it.hasNext()).isFalse();
    }

    @Test
    void shouldFindElementWhenNotEmpty() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);
        Iterator<Integer> itr = set.iterator();

        assertThat(itr.hasNext()).isTrue();
        assertThat(itr.next()).isEqualTo(eins);
        assertThat(itr.next()).isEqualTo(zwei);
        assertThat(itr.next()).isEqualTo(drei);
        assertThat(itr.hasNext()).isFalse();
    }

    @Test
    void shouldCloneSpecifiedHashSet() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        HashSet<Integer> cloneSet = new HashSet<Integer>();
        cloneSet = (HashSet) set.clone();

        assertThat(cloneSet).contains(eins);
        assertThat(cloneSet).contains(zwei);
        assertThat(cloneSet).contains(drei);
        assertThat(cloneSet).isNotEmpty();
        assertThat(cloneSet).isEqualTo(set);
    }
}
