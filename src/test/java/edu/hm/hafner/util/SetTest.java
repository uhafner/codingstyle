package edu.hm.hafner.util;

//Standard Imports for Unit Test
import org.junit.jupiter.api.Test;
//Use-Case Imports for Unit Test
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
//Import the class who should be tested
import java.util.Set;
import java.util.HashSet;

/**
 * Tests the class HashSet, with usage of SUT HashSet from Integer.
 *
 * @author Philipp Keidler
 */
public class SetTest {
    @Test
    void constructions() {
        //Test: public HashSet() (standard constructor)
        assertThat(new HashSet<Integer>()).isNotNull();
    }

    @Test
    void iterator() { }

    @Test
    void size() { }

    @Test
    void isEmpty() { }

    @Test
    void contains() { }

    @Test
    void add() { }

    @Test
    void remove() { }

    @Test
    void clear() { }

    @Test
    void cloneObject() { }

    @Test
    void writeObject() { }

    @Test
    void readObject() { }
}
