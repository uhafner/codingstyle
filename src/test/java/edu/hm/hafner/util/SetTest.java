package edu.hm.hafner.util;

//Standard Imports for Unit Test
import org.junit.jupiter.api.Test;
//Imports for Unit Test only when needed
import static org.assertj.core.api.Assertions.*;
//Import the class who should be tested
import java.util.Set;
import java.util.HashSet;

/**
 * Tests the class HashSet, with usage of SUT HashSet from Integer.
 *
 * @author Philipp Keidler
 */
public class SetTest {
    private Set<Integer> testHash;
    private Set<Integer> extendHash;

    @Test
    void constructions() {
        /* Test: HashSet() (standard constructor) */
        testHash = new HashSet<Integer>();
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        /* Test: HashSet(int initialCapacity) */
        /* Test: HashSet(int initialCapacity, float loadFactor) */
        /* Test: HashSet(int initialCapacity, float loadFactor, boolean dummy) */
        /* Test: HashSet(Collection<? extends E> c) */
    }

    @Test
    void iterator() { }

    @Test
    void size() {
        testPrep();

        /* Test: Sizes after declaration. */
        assertThat(testHash.size()).isEqualTo(0);
        assertThat(extendHash.size()).isEqualTo(104);

        /* Test: Size after Adding something. */
        testHash.addAll(extendHash);
        assertThat(testHash.size()).isEqualTo(104);
        testHash.add(404);
        assertThat(testHash.size()).isEqualTo(105);
        testHash.add(404); //Duplicates are not added.
        assertThat(testHash.size()).isEqualTo(105);

        /* Test: Size after removing something. */
        testHash.remove(404);
        assertThat(testHash.size()).isEqualTo(104);
        testHash.removeAll(extendHash);
        assertThat(testHash.size()).isEqualTo(0);
    }

    @Test
    void isEmpty() {
        testPrep();

        /* Test: After creating a fresh Variable */
        assertThat(testHash.isEmpty()).isTrue();

        /* Test: Adding things (not empty anymore), it has to stay FALSE */
        testHash.add(511);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(2106);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(1310);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(2401);
        assertThat(testHash.isEmpty()).isFalse();

        /* Test: Removing thing (until empty), it should getting TRUE after removing the last Object */
        testHash.remove(511);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(2106);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(1310);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(2401);
        assertThat(testHash.isEmpty()).isTrue();

        /* Test: Adding and removing more than one Object. Test if isEmpty() acts as expected. */
        testHash.addAll(extendHash);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.removeAll(extendHash);
        assertThat(testHash.isEmpty()).isTrue();
    }

    @Test
    void contains() {
        testPrep();

        assertThat(extendHash.contains(511)).isTrue();
        assertThat(extendHash.contains(2401)).isTrue();
        for (int i = 100; i < 150; i++) {
            assertThat(extendHash.contains(i)).isFalse();
        }
    }

    @Test //additional
    void containsAll() { }

    @Test
    void add() { }

    @Test
    void remove() { }

    @Test
    void clear() {
        testPrep();
        extendHash.clear();
        assertThat(extendHash.isEmpty()).isTrue();
    }

    @Test
    void cloneObject() { }

    @Test
    void writeObject() { }

    @Test
    void readObject() { }

    /**
     * Sets the test variable to the starting point.
     */
    void testPrep() {
        testHash = new HashSet<>();
        extendHash = new HashSet<>();
        extendHash.add(511);
        extendHash.add(2106);
        extendHash.add(1310);
        extendHash.add(2401);
        for (int i = 0; i < 100; i++) {
            extendHash.add(i);
        }
    }
}
