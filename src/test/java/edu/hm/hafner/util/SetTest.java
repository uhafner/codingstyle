package edu.hm.hafner.util;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import java.util.HashSet;
import java.util.Iterator;

public class SetTest {

    @Test
    void shouldCreateEqualHashSets() {
        HashSet<Integer> testSet = new HashSet<>();
        HashSet<Integer> testSet1 = new HashSet<>(16);
        HashSet<Integer> testSet2 = new HashSet<> (16, 0.75f);

        assertThat(testSet).isEqualTo(testSet1);
        assertThat(testSet1).isEqualTo(testSet2);
    }

    @Test
    void shouldIterateOverHashSet() {
        HashSet<Integer> testSet = new HashSet<>(3);
        HashSet<Integer> emptyTestSet = new HashSet<>(0);

        testSet.add(1);
        testSet.add(2);
        testSet.add(3);

        Iterator<Integer> iterator = testSet.iterator();
        for (int i = 1; iterator.hasNext(); i++) {
            assertThat(iterator.next()).isEqualTo(i);
        }

        Iterator<Integer> emptyIterator = emptyTestSet.iterator();

        assertThat(iterator.hasNext()).isFalse();
        assertThat(emptyIterator.hasNext()).isFalse();
    }

    @Test
    void shouldReturnCorrectSize() {
        HashSet<Integer> testSet = new HashSet<>(3);

        testSet.add(1);
        testSet.add(2);
        testSet.add(3);

        assertThat(testSet.size()).isEqualTo(3);
        assertThat(testSet.size()).isNotEqualTo(4);
    }

    @Test
    void hashSetShouldBeEmpty() {
        HashSet<Integer> testSet = new HashSet<>(0);

        assertThat(testSet.isEmpty()).isTrue();

        testSet.add(1);

        assertThat(testSet.isEmpty()).isFalse();
    }

    @Test
    void hashSetShouldContainValue() {
        HashSet<Integer> testSet = new HashSet<>(1);

        testSet.add(1);

        assertThat(testSet.contains(1)).isTrue();
        assertThat(testSet.contains(2)).isFalse();
    }

    @Test
    void shouldRemoveValueFromHashSet() {
        HashSet<Integer> testSet = new HashSet<>(2);

        testSet.add(1);
        testSet.add(2);

        assertThat(testSet.contains(1)).isTrue();
        assertThat(testSet.contains(2)).isTrue();

        testSet.remove(1);

        assertThat(testSet.contains(1)).isFalse();
        assertThat(testSet.contains(3)).isFalse();
        assertThat(testSet.remove(3)).isFalse();
    }

    @Test
    void shouldClearHashSet() {
        HashSet<Integer> testSet = new HashSet<>(2);

        testSet.add(1);
        testSet.add(2);

        assertThat(testSet.contains(1)).isTrue();
        assertThat(testSet.contains(2)).isTrue();

        testSet.clear();

        assertThat(testSet.contains(1)).isFalse();
        assertThat(testSet.contains(2)).isFalse();
    }

    @Test
    void shouldCloneHashSet() {
        HashSet<Integer> testSet = new HashSet<>(2);
        HashSet<Integer> emptyTestSet;

        testSet.add(1);
        testSet.add(2);

        assertThat(testSet).isEqualTo(testSet.clone());

        emptyTestSet = (HashSet<Integer>) testSet.clone();

        assertThat(emptyTestSet).isEqualTo(testSet);

        //assertThatThrownBy(() -> {
            //exception werfen
        //}).isInstanceOf(CloneNotSupportedException.class);
    }
}
