package edu.hm.hafner.util;
import static org.assertj.core.api.Assertions.*;
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



    @Test
    void shouldCreateEmptySet() {
        HashSet<Integer> testSet = creatHashSet(0);

        assertThat(testSet).isNotNull();
        assertThat(testSet).isEmpty();
        assertThat(testSet).hasSize(0);// possible because AbstractSet implements Iterable
    }

    @Test
    void shouldCreateGivenSet() {
        ArrayList<Integer> copyList = new ArrayList<>();
        copyList.add(0);
        copyList.add(1);
        copyList.add(2);
        HashSet<Integer> testSet = new HashSet<>(copyList);

        assertThat(testSet).containsExactlyInAnyOrder(0,1,2);
    }

    @Test
    void shouldReturnIterator() {
        HashSet<Integer> testSet = creatHashSet(3);
        Iterator<Integer> testIterator = testSet.iterator();

        assertThat(testIterator.hasNext()).isTrue();
        assertThat(testIterator.next()).isBetween(0,2);
        assertThat(testIterator.hasNext()).isTrue();
        assertThat(testIterator.next()).isBetween(0,2);
        assertThat(testIterator.hasNext()).isTrue();
        assertThat(testIterator.next()).isBetween(0,2);
        assertThat(testIterator.hasNext()).isFalse();


        HashSet<Integer> emptyTestSet = creatHashSet(0);
        Iterator<Integer> emptyTestIterator = emptyTestSet.iterator();

        assertThat(emptyTestIterator.hasNext()).isFalse();


        HashSet<Integer> testSetWithNull = createHashSetWithOnlyNull();
        Iterator<Integer> nullIterator = testSetWithNull.iterator();

        assertThat(nullIterator.hasNext()).isTrue();
    }

    @Test
    void shouldReturnCorrectSize() {
        HashSet<Integer> testSetZero = creatHashSet(0);
        assertThat(testSetZero).hasSize(0);

        HashSet<Integer> testSetOne = creatHashSet(1);
        assertThat(testSetOne).hasSize(1);

        HashSet<Integer> testSetTwo = creatHashSet(2);
        assertThat(testSetTwo).hasSize(2);

        HashSet<Integer> testSetHundred = creatHashSet(100);
        assertThat(testSetHundred).hasSize(100);

        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();
        assertThat(testSetWithNull).hasSize(4);
    }

    @Test
    void shouldTestForEmptiness() {
        HashSet<Integer> testSetEmpty = creatHashSet(0);
        HashSet<Integer> testSetSizeOne = creatHashSet(1);
        HashSet<Integer> testSetWithNull = createHashSetWithOnlyNull();

        assertThat(testSetEmpty.isEmpty()).isTrue();
        assertThat(testSetEmpty).isEmpty();
        assertThat(testSetSizeOne.isEmpty()).isFalse();
        assertThat(testSetSizeOne).isNotEmpty();
        assertThat(testSetWithNull.isEmpty()).isFalse();
        assertThat(testSetWithNull).isNotEmpty();
    }

    @Test
    void shouldTestForContainment() {
        HashSet<Integer> testSet = createHashSetWithNullAndSize4();

        assertThat(testSet.contains(0)).isTrue();
        assertThat(testSet.contains(1)).isTrue();
        assertThat(testSet.contains(2)).isTrue();
        assertThat(testSet.contains(null)).isTrue();
        assertThat(testSet.contains(3)).isFalse();
        assertThat(testSet.contains(-1)).isFalse();
    }

    @Test
    void shouldAddItem() {
        HashSet<Integer> testSet = creatHashSet(0);
        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();

        // add string, add existing item
        assertThat(testSet).isEmpty();
        assertThat(testSet.add(0)).isTrue();
        assertThat(testSet).isNotEmpty();
        assertThat(testSet.add(1)).isTrue();
        assertThat(testSet).isNotEmpty();

        assertThat(testSet.add(0)).isFalse();
        assertThat(testSetWithNull.add(null)).isFalse();
    }

    @Test
    void shouldRemoveItem() {
        HashSet<Integer> testSet = createHashSetWithNullAndSize4();

        assertThat(testSet.isEmpty()).isFalse();
        assertThat(testSet.contains(2)).isTrue();
        assertThat(testSet.remove(2)).isTrue();
        assertThat(testSet.contains(2)).isFalse();

        assertThat(testSet.contains(3)).isFalse();
        assertThat(testSet.remove(3)).isFalse();

        assertThat(testSet.contains(null)).isTrue();
        assertThat(testSet.remove(null)).isTrue();
        assertThat(testSet.contains(null)).isFalse();
        assertThat(testSet.remove(null)).isFalse();
    }

    @Test
    void shouldClearSet() {
        HashSet<Integer> emptyTestSet = creatHashSet(0);
        HashSet<Integer> testSetWithNull = createHashSetWithNullAndSize4();
        HashSet<Integer> testSet = creatHashSet(20);

        assertThat(emptyTestSet).isEmpty();
        assertThat(testSetWithNull).isNotEmpty();
        testSetWithNull.clear();
        assertThat(testSetWithNull).isEmpty();
        assertThat(testSet).isNotEmpty();
        testSet.clear();
        assertThat(testSet).isEmpty();
    }

    @Test
    void shouldCloneSet() {
        HashSet<Integer> emptyTestSet = creatHashSet(0);
        HashSet<Integer> testSetWitNull = createHashSetWithNullAndSize4();
        HashSet<Integer> testSet = creatHashSet(3);
        HashSet<Integer> changedSet;

        assertThat(emptyTestSet).isEqualTo(emptyTestSet.clone());
        changedSet = (HashSet<Integer>) emptyTestSet.clone();
        changedSet.add(null);
        assertThat(emptyTestSet).isNotEqualTo(changedSet);
        changedSet.remove(null);
        changedSet.add(0);
        assertThat(emptyTestSet).isNotEqualTo(changedSet);

        assertThat(testSetWitNull).isEqualTo(testSetWitNull.clone());
        changedSet = (HashSet<Integer>) testSetWitNull.clone();
        changedSet.add(3);
        assertThat(testSetWitNull).isNotEqualTo(changedSet);

        assertThat(testSet).isEqualTo(testSet.clone());
        changedSet = (HashSet<Integer>) testSetWitNull.clone();
        changedSet.add(null);
        assertThat(testSet).isNotEqualTo(changedSet);
        changedSet.remove(null);
        changedSet.add(3);
        assertThat(testSet).isNotEqualTo(changedSet);
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
