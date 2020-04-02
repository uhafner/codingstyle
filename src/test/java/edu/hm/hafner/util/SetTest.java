package edu.hm.hafner.util;
//Standard Import for Unit Test
import org.junit.jupiter.api.Test;
//Imports for Unit Tests
import static org.assertj.core.api.Assertions.*;
//Import the testing classes
import java.util.Set;
import java.util.HashSet;

/**
 * JUnitTests for the class HashSet.
 *
 * @author Nina Grabow
 */

public class SetTest {
    private Set<Integer> testHash;
    private Set<Integer> extendHash;

    @Test
    void constructions() {

        final int initialCapacity = 3; // >= 0
        final float loadFactor = (float) 1.0; // > 0

        // Test: standard constructor
        testHash = new HashSet<>();
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        // Test: HashSet(int initialCapacity)
        testHash = new HashSet<>(initialCapacity);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        // Test: HashSet(int initialCapacity, float loadFactor)
        testHash = new HashSet<>(initialCapacity, loadFactor);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isEmpty();

        // Test: HashSet(Collection<? extends E> c)
        testInitializing();
        testHash = new HashSet<>(extendHash);
        assertThat(testHash).isNotNull();
        assertThat(testHash).isNotEmpty();
        testHash.add(3101);
        assertThat(testHash.size()).isEqualTo(5);
        assertThat(extendHash.size()).isEqualTo(4); //that shows a deep copy, NOT a pointer copy!
    }


    @Test
    void iterator() {
        //Test configuration
        testHash = new HashSet<>();
        testHash.add(4);
        testHash.add(500);

        //Test
        assertThat(testHash.iterator().hasNext()).isTrue();
        assertThat(testHash.iterator().next()).isEqualTo(4);
        testHash.remove(4);
        assertThat(testHash.iterator().next()).isEqualTo(500);
        testHash.remove(500);
        assertThat(testHash.iterator().hasNext()).isFalse();
    }
        
    @Test
    void size() {
        testInitializing();

        //Test: Sizes after declaration.
        assertThat(testHash.size()).isEqualTo(0);
        assertThat(extendHash.size()).isEqualTo(4);

        // Test: Adding values
        testHash.addAll(extendHash);
        assertThat(testHash.size()).isEqualTo(4);
        testHash.add(360);
        assertThat(testHash.size()).isEqualTo(5);
        testHash.add(360);
        assertThat(testHash.size()).isEqualTo(5);

        // Test: Size after removing a value.
        testHash.remove(360);
        assertThat(testHash.size()).isEqualTo(4);
        testHash.removeAll(extendHash);
        assertThat(testHash.size()).isEqualTo(0);
    }

    @Test
    void isEmpty() {
        testInitializing();

        //Test: Creating a new variable
        assertThat(testHash.isEmpty()).isTrue();

        //Test: Adding values, so the hash is not empty any more.
        testHash.add(2503);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(1106);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(2212);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(709);
        assertThat(testHash.isEmpty()).isFalse();

        //Test: Removing all values in the hash step by step.
        testHash.remove(2503);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(1106);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(2212);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(709);
        assertThat(testHash.isEmpty()).isTrue();

        // Test: Adding and removing more than one Object.
        testHash.addAll(extendHash);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.removeAll(extendHash);
        assertThat(testHash.isEmpty()).isTrue();
    }

    @Test
    void contains() {
        testInitializing();
        assertThat(extendHash.contains(1106)).isTrue();
        assertThat(extendHash.contains(2503)).isTrue();
        assertThat(extendHash.contains(2212)).isTrue();
        assertThat(extendHash.contains(709)).isTrue();
        assertThat(extendHash.contains(4)).isFalse();
        assertThat(extendHash.contains(0)).isFalse();
        assertThat(extendHash.contains(1999)).isFalse();
    }

    @Test
    void add() {
        testHash = new HashSet<>();
        assertThat(testHash).isEmpty();
        testHash.add(50);
        assertThat(testHash.hashCode()).isEqualTo(50);
        testHash.add(443);
        assertThat(testHash.hashCode()).isEqualTo(493); //50+443
    }

    @Test
    void remove() {
        testInitializing();
        assertThat(extendHash).isNotNull();
        assertThat(extendHash.hashCode()).isEqualTo(6530); //All extendedHash integers summed
        extendHash.remove(709);
        assertThat(extendHash.hashCode()).isEqualTo(5821); //All extendedHash integers summed without 709
        extendHash.remove(1106);
        extendHash.remove(2503);
        extendHash.remove(2212);
        assertThat(extendHash.hashCode()).isEqualTo(0);
        assertThat(extendHash).isEmpty();
    }

    @Test
    void clear() {
        testInitializing();
        extendHash.clear();
        assertThat(extendHash).isEmpty();
    }

    /**
     * Test variables initializing.
     */
    void testInitializing() {
        testHash = new HashSet<>(); //empty
        extendHash = new HashSet<>();
        extendHash.add(1106);
        extendHash.add(2503);
        extendHash.add(2212);
        extendHash.add(709);
    }
}