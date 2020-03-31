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
        final int initialCapacity = 4; // has to be: >= 0 !
        final float loadFactor = (float) 100.0; // has to be: > 0 !

        /* Test: HashSet() (standard constructor) */
        testHash = new HashSet<>();
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        /* Test: HashSet(int initialCapacity) */
        testHash = new HashSet<>(initialCapacity);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        /* Test: HashSet(int initialCapacity, float loadFactor) */
        testHash = new HashSet<>(initialCapacity, loadFactor);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        /* Test: HashSet(int initialCapacity, float loadFactor, boolean dummy) is PRIVATE! => NO test */

        /* Test: HashSet(Collection<? extends E> c) */
        testPrep();
        testHash = new HashSet<>(extendHash);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isNotEmpty();
        testHash.add(2412);
        assertThat(testHash.size()).isNotEqualTo(extendHash.size()); //that shows a deep copy, NOT a pointer copy!
    }

    @Test
    void iterator() {
        testPrep();

        /* Check: hasNext() true and false */
        assertThat(testHash.iterator().hasNext()).isFalse();
        assertThat(extendHash.iterator().hasNext()).isTrue();

        testHash.addAll(extendHash);

        /* Check: First Integer in Map */
        assertThat(testHash.iterator().next()).isEqualTo(0);
    }

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

        /* Test: Positive test of contains() */
        assertThat(extendHash.contains(511)).isTrue();
        assertThat(extendHash.contains(2401)).isTrue();
        for (int i = 75; i < 100; i++) {
            assertThat(extendHash.contains(i)).isTrue();
        }

        /* Test: Negative test of contains() */
        assertThat(extendHash.contains(3112)).isFalse();
        assertThat(extendHash.contains(1996)).isFalse();
        for (int i = 100; i < 150; i++) {
            assertThat(extendHash.contains(i)).isFalse();
        }
    }

    @Test //additional
    void containsAll() {
        testPrep();

        /* Test: Check a filled Map against an empty one */
        assertThat(extendHash.containsAll(testHash)).isTrue();
        assertThat(testHash.containsAll(extendHash)).isFalse();

        /* Test: Check exactly same filled Maps against each other */
        testHash.addAll(extendHash);
        assertThat(extendHash.containsAll(testHash)).isTrue();
        assertThat(testHash.containsAll(extendHash)).isTrue();

        /* Test: Check two different filled Maps against each other */
        testHash.add(3112);
        assertThat(extendHash.containsAll(testHash)).isFalse();
        assertThat(testHash.containsAll(extendHash)).isTrue();
        extendHash.add(2412);
        assertThat(extendHash.containsAll(testHash)).isFalse();
        assertThat(testHash.containsAll(extendHash)).isFalse();
        testHash.remove(3112);
        testHash.remove(511);
        assertThat(extendHash.containsAll(testHash)).isTrue();
        assertThat(testHash.containsAll(extendHash)).isFalse();

        /* Test: Check the Map against itself */
        testHash = extendHash;
        assertThat(extendHash.containsAll(testHash)).isTrue();
        assertThat(testHash.containsAll(extendHash)).isTrue();
    }

    @Test
    void add() {
        testHash = new HashSet<>();
        extendHash = new HashSet<>();

        /* Test: Adding only one Integer each time */
        assertThat(extendHash).isEmpty();
        assertThat(extendHash.hashCode()).isEqualTo(0);
        for (int i = 1; i <= 25; i++) {
            extendHash.add(i);
        }
        assertThat(extendHash).isNotEmpty();
        assertThat(extendHash.size()).isEqualTo(25);
        assertThat(extendHash.hashCode()).isEqualTo(325);

        /* Test: Adding more than one at once */
        assertThat(testHash).isEmpty();
        assertThat(testHash.hashCode()).isEqualTo(0);
        testHash.addAll(extendHash);
        assertThat(testHash).isNotEmpty();
        assertThat(testHash.size()).isEqualTo(25);
        assertThat(testHash.hashCode()).isEqualTo(325);
        assertThat(testHash).isEqualTo(extendHash);

        /* Test: Adding something that already exist (nothing is then happening) */
        testHash.add(25);
        assertThat(testHash).isEqualTo(extendHash);
    }

    @Test
    void remove() {
        testPrep();
        testHash.addAll(extendHash);

        /* Test: Removing only one Integer each time */
        testHash.remove(13);
        assertThat(testHash.hashCode()).isEqualTo(extendHash.hashCode() - 13);
        testHash.remove(77);
        assertThat(testHash.hashCode()).isEqualTo(extendHash.hashCode() - 77 - 13);
        testHash.remove(7);
        assertThat(testHash.hashCode()).isEqualTo(extendHash.hashCode() - 7 - 77 - 13);

        /* Test: Removing more than one at once */
        extendHash.removeAll(testHash);
        assertThat(extendHash.hashCode()).isEqualTo(7 + 13 + 77);

        /* Test: Removing something that not exist (nothing is then happening) */
        testHash = new HashSet<>(extendHash);
        testHash.remove(2);
        assertThat(testHash).isEqualTo(extendHash);
    }

    @Test
    void clear() {
        testPrep();
        extendHash.clear();
        assertThat(extendHash).isEmpty();
        assertThat(extendHash).isNotNull();
    }

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
