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
        HashSet<Integer> set = new HashSet();
        Assertions.assertThat(set).isEmpty();
    }

    @Test
    void shouldTestConstructorCollection() {
        Assertions.assertThatThrownBy(() -> new HashSet<Integer>(null))
                .isExactlyInstanceOf(NullPointerException.class)
                .as("If the specified collection is null, throws NullPointerException");
    }

    @Test
    void shouldTestConstructorCapacity() {
        Assertions.assertThatThrownBy(() -> new HashSet<Integer>(-1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero, throws IllegalArgumentException");
    }

    @Test
    void shouldTestConstructorCapacityLoadFactor() {
        Assertions.assertThatThrownBy(() -> new HashSet<Integer>(-1, -15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");

        Assertions.assertThatThrownBy(() -> new HashSet<Integer>(1, -15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");

        Assertions.assertThatThrownBy(() -> new HashSet<Integer>(-1, 15))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .as("If capacity less than zero or loadFactor nonpositve,"
                        + " throws IllegalArgumentException");
    }

    @Test
    void shouldAddElements() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);

        Assertions.assertThat(set.add(drei)).isTrue();
        Assertions.assertThat(set.add(zwei)).isFalse();
    }

    @Test
    void shouldContainElements() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);

        Assertions.assertThat(set).contains(eins, zwei);
        Assertions.assertThat(set).doesNotContain(vier);
    }

    @Test
    void shouldReturnTrueWhenEmpty() {
        HashSet<Integer> set = new HashSet();
        Assertions.assertThat(set).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenNotEmpty() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        Assertions.assertThat(set).isNotEmpty();
    }

    @Test
    void shouldRemoveElements() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        Assertions.assertThat(set.remove(eins)).isTrue();
        Assertions.assertThat(set.remove(eins)).isFalse();
    }

    @Test
    void shouldClearAllElements() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        set.clear();
        Assertions.assertThat(set).isEmpty();
    }

    @Test
    void shouldReturnCorrectSize() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        Assertions.assertThat(set).hasSize(drei);
        set.remove(drei);
        Assertions.assertThat(set).hasSize(zwei);
        set.clear();
        Assertions.assertThat(set).hasSize(0);
    }

    @Test
    void shouldNotFindElementWhenEmpty() {
        HashSet<Integer> set = new HashSet();
        Iterator<Integer> itr = set.iterator();

        Assertions.assertThat(itr.hasNext()).isFalse();
    }

    @Test
    void shouldFindElementWhenNotEmpty() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);
        Iterator<Integer> itr = set.iterator();

        Assertions.assertThat(itr.hasNext()).isTrue();
        Assertions.assertThat(itr.next()).isEqualTo(eins);
        Assertions.assertThat(itr.next()).isEqualTo(zwei);
        Assertions.assertThat(itr.next()).isEqualTo(drei);
        Assertions.assertThat(itr.hasNext()).isFalse();
    }

    @Test
    void shouldCloneSpecifiedHashSet() {
        HashSet<Integer> set = new HashSet();
        set.add(eins);
        set.add(zwei);
        set.add(drei);

        HashSet<Integer> cloneSet = new HashSet<Integer>();
        cloneSet = (HashSet) set.clone();

        assertThat(cloneSet).contains(eins, zwei, drei);
        assertThat(cloneSet).isNotEmpty();
        assertThat(cloneSet).isEqualTo(set);
    }
}
