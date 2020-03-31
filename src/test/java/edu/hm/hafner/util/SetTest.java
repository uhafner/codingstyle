package edu.hm.hafner.util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Test the class {@link java.util.HashSet} for integers.
 *
 * @author Johannes Reichle
 */
class SetTest {

    // methods usable that are not tested yet???
    @Test
    void shouldCreateEmptySet() {
        HashSet<Integer> testSet = creatHashSet(0);

        Assertions.assertThat(testSet).isNotNull();
        Assertions.assertThat(testSet).isEmpty(); // possible because AbstractSet implements Iterable
    }

    @Test
    void shouldCreateGivenSet() {
        ArrayList<Integer> copyList = new ArrayList<>();
        copyList.add(0);
        copyList.add(1);
        copyList.add(2);
        HashSet<Integer> testSet = new HashSet<>(copyList);

        Assertions.assertThat(testSet).isNotNull();
        Assertions.assertThat(testSet).containsExactlyInAnyOrder(0,1,2);
    }

    @Test
    void shouldReturnIterator() {
        HashSet<Integer> testSet = creatHashSet(3);
        Iterator<Integer> testIterator = testSet.iterator();
        HashSet<Integer> emptyTestSet = creatHashSet(0);
        Iterator<Integer> emptyTestIterator = emptyTestSet.iterator();
        HashSet<Integer> testSetWithNull = createHashSetWithOnlyNull();
        Iterator<Integer> nullIterator = testSetWithNull.iterator();

        Assertions.assertThat(testIterator.hasNext()).isTrue();
        Assertions.assertThat(testIterator.next()).isBetween(0,2);
        Assertions.assertThat(testIterator.hasNext()).isTrue();
        Assertions.assertThat(testIterator.next()).isBetween(0,2);
        Assertions.assertThat(testIterator.hasNext()).isTrue();
        Assertions.assertThat(testIterator.next()).isBetween(0,2);
        Assertions.assertThat(testIterator.hasNext()).isFalse();

        Assertions.assertThat(emptyTestIterator.hasNext()).isFalse();

        Assertions.assertThat(nullIterator.hasNext()).isTrue();
    }

    @Test
    void shouldReturnCorrectSize() {
        HashSet<Integer> testSetZero = creatHashSet(0);
        HashSet<Integer> testSetOne = creatHashSet(1);
        HashSet<Integer> testSetTwo = creatHashSet(2);
        HashSet<Integer> testSetHundred = creatHashSet(100);
        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();

        Assertions.assertThat(testSetZero.size() == 0).isTrue();
        Assertions.assertThat(testSetOne.size() == 1).isTrue();
        Assertions.assertThat(testSetTwo.size() == 2).isTrue();
        Assertions.assertThat(testSetHundred.size() == 100).isTrue();
        Assertions.assertThat(testSetWithNull.size() == 4).isTrue();
    }

    @Test
    void shouldTestForEmptiness() {
        HashSet<Integer> testSetEmpty = creatHashSet(0);
        HashSet<Integer> testSetSizeOne = creatHashSet(4);
        HashSet<Integer> testSetWithNull = createHashSetWithOnlyNull();

        Assertions.assertThat(testSetEmpty.isEmpty()).isTrue();
        Assertions.assertThat(testSetSizeOne.isEmpty()).isFalse();
        Assertions.assertThat(testSetWithNull.isEmpty()).isFalse();
    }

    @Test
    void shouldTestForContainment() {
        HashSet<Integer> testSet = createHashSetWithNullAndSize4();

        Assertions.assertThat(testSet.contains(0)).isTrue();
        Assertions.assertThat(testSet.contains(1)).isTrue();
        Assertions.assertThat(testSet.contains(2)).isTrue();
        Assertions.assertThat(testSet.contains(null)).isTrue();
        Assertions.assertThat(testSet.contains(3)).isFalse();
        Assertions.assertThat(testSet.contains(-1)).isFalse();
    }

    @Test
    void shouldAddItem() {
        HashSet<Integer> testSet = creatHashSet(0);
        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();

        // add string, add existing item
        Assertions.assertThat(testSet.isEmpty()).isTrue();
        Assertions.assertThat(testSet.add(0)).isTrue();
        Assertions.assertThat(testSet.isEmpty()).isFalse();
        Assertions.assertThat(testSet.add(1)).isTrue();
        Assertions.assertThat(testSet.isEmpty()).isFalse();

        Assertions.assertThat(testSet.add(0)).isFalse();
        Assertions.assertThat(testSetWithNull.add(null)).isFalse();
    }

    @Test
    void shouldRemoveItem() {
         HashSet<Integer> testSet = createHashSetWithNullAndSize4();

         Assertions.assertThat(testSet.isEmpty()).isFalse();
         Assertions.assertThat(testSet.contains(2)).isTrue();
         Assertions.assertThat(testSet.remove(2)).isTrue();
         Assertions.assertThat(testSet.contains(2)).isFalse();

         Assertions.assertThat(testSet.contains(3)).isFalse();
         Assertions.assertThat(testSet.remove(3)).isFalse();

         Assertions.assertThat(testSet.contains(null)).isTrue();
         Assertions.assertThat(testSet.remove(null)).isTrue();
         Assertions.assertThat(testSet.contains(null)).isFalse();
         Assertions.assertThat(testSet.remove(null)).isFalse();
    }

    @Test
    void shouldClearSet() {
        HashSet<Integer> emptyTestSet = creatHashSet(0);
        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();
        HashSet<Integer> testSet = creatHashSet(20);

        Assertions.assertThat(emptyTestSet).isEmpty();
        Assertions.assertThat(testSetWithNull).isNotEmpty();
        testSetWithNull.clear();
        Assertions.assertThat(testSetWithNull).isEmpty();
        Assertions.assertThat(testSet).isNotEmpty();
        testSet.clear();
        Assertions.assertThat(testSet).isEmpty();
    }

    @Test
    void shouldCloneSet() {
        HashSet<Integer> emptyTestSet = creatHashSet(0);
        HashSet<Integer> testSetWitNull = createHashSetWithNullAndSize4();
        HashSet<Integer> testSet = creatHashSet(3);
        HashSet<Integer> changedSet;

        Assertions.assertThat(emptyTestSet).isEqualTo(emptyTestSet.clone());
        changedSet = (HashSet<Integer>) emptyTestSet.clone();
        changedSet.add(null);
        Assertions.assertThat(emptyTestSet).isNotEqualTo(changedSet);
        changedSet.remove(null);
        changedSet.add(0);
        Assertions.assertThat(emptyTestSet).isNotEqualTo(changedSet);

        Assertions.assertThat(testSetWitNull).isEqualTo(testSetWitNull.clone());
        changedSet = (HashSet<Integer>) testSetWitNull.clone();
        changedSet.add(3);
        Assertions.assertThat(testSetWitNull).isNotEqualTo(changedSet);

        Assertions.assertThat(testSet).isEqualTo(testSet.clone());
        changedSet = (HashSet<Integer>) testSetWitNull.clone();
        changedSet.add(null);
        Assertions.assertThat(testSet).isNotEqualTo(changedSet);
        changedSet.remove(null);
        changedSet.add(3);
        Assertions.assertThat(testSet).isNotEqualTo(changedSet);
    }



    private HashSet<Integer> creatHashSet(final int length) {
        HashSet<Integer> testSet = new HashSet<>();

        for (int i = 0; i < length; i++) {
            testSet.add(i);
        }

        return testSet;
    }

    private HashSet<Integer> createHashSetWithNullAndSize4() {
        HashSet<Integer> setWithNull = creatHashSet(3);
        setWithNull.add(null);
        return setWithNull;
    }

    private HashSet<Integer> createHashSetWithOnlyNull() {
        HashSet<Integer> setWithNull = creatHashSet(0);
        setWithNull.add(null);
        return setWithNull;
    }
}
