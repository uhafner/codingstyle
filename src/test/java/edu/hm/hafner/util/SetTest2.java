package edu.hm.hafner.util;

//Imports
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

/**
 * Test the class {@link java.util.HashSet}.
 *
 * @author Markus Vierheilig
 */

public class SetTest2 {
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
        testHash.add(2608);
        assertThat(testHash.size()).isEqualTo(5);
        assertThat(extendHash.size()).isEqualTo(4); //deep copy, NOT a pointer copy!
    }



    @Test
    void iterator() {
        //Test configuration
        testHash = new HashSet<>();
        testHash.add(4);
        testHash.add(471);

        //Test
        assertThat(testHash.iterator().hasNext()).isTrue();
        assertThat(testHash.iterator().next()).isEqualTo(4);
        testHash.remove(4);
        assertThat(testHash.iterator().next()).isEqualTo(471);
        testHash.remove(471);
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
        testHash.add(555);
        assertThat(testHash.size()).isEqualTo(5);
        testHash.add(555);
        assertThat(testHash.size()).isEqualTo(5);

        // Test: Size after removing a value.
        testHash.remove(555);
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
        testHash.add(1997);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(1999);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(1900);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.add(123);
        assertThat(testHash.isEmpty()).isFalse();

        //Test: Removing all values in the hash step by step.
        testHash.remove(1997);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(1999);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(1900);
        assertThat(testHash.isEmpty()).isFalse();
        testHash.remove(123);
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
        assertThat(extendHash.contains(1999)).isTrue();
        assertThat(extendHash.contains(1997)).isTrue();
        assertThat(extendHash.contains(1900)).isTrue();
        assertThat(extendHash.contains(123)).isTrue();
        assertThat(extendHash.contains(4)).isFalse();
        assertThat(extendHash.contains(0)).isFalse();
        assertThat(extendHash.contains(1860)).isFalse();
    }

    @Test
    void add() {
        testHash = new HashSet<>();
        assertThat(testHash).isEmpty();
        testHash.add(11);
        assertThat(testHash.hashCode()).isEqualTo(11);
        testHash.add(300);
        assertThat(testHash.hashCode()).isEqualTo(311); //11+300
    }

    @Test
    void remove() {
        testInitializing();
        assertThat(extendHash).isNotNull();
        assertThat(extendHash.hashCode()).isEqualTo(6019); //All extendedHash integers summed
        extendHash.remove(123);
        assertThat(extendHash.hashCode()).isEqualTo(5896); //All extendedHash integers summed without 123
        extendHash.remove(1999);
        extendHash.remove(1997);
        extendHash.remove(1900);
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
        extendHash.add(1999);
        extendHash.add(1997);
        extendHash.add(1900);
        extendHash.add(123);
    }
}