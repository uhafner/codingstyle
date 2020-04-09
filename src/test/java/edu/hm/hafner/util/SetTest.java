package edu.hm.hafner.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
/**

 * Tests the class {@link HashSet} and {@link Set}
 *
 * @author Manuel Knickl
 */
public class SetTest {
    /**
     * Test Constructors.
     */
    @Test
    void shouldTestConstructors(){
        HashSet set;

        //HashSet()
        set = new HashSet();
        assertThat(set).isEmpty();

        //HashSet(int initialCapacity)
        set = new HashSet(1);
        assertThat(set).isEmpty();

        //HashSet(int initialCapacity, float loadFactor)
        set = new HashSet(1, 1);
        assertThat(set).isEmpty();

        //HashSet(Collection<? extends E> c)
        set = new HashSet(Arrays.asList(0, 1, 2, 3));
        assertThat(set).isNotEmpty();

        //Test Exceptions
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> new HashSet(null));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet(-1));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet(1,
                0));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet(1,
                -1));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet(-1,
                0.5f));
    }

    /**
     * Test method iterator.
     */
    @Test
    void shouldIterator(){
        //Given
        HashSet set = new HashSet();
        //Then
        assertThat(set.iterator().hasNext()).isFalse();
        //When
        set.add(1);
        set.add(2);
        //Then
        assertThat(set.iterator().hasNext()).isTrue();
        assertThat(set.iterator().next()).isEqualTo(1);
        set.remove(1);
        assertThat(set.iterator().next()).isEqualTo(2);
        set.remove(2);
        assertThat(set.iterator().hasNext()).isFalse();
    }
    /**
     * Test method isEmpyty.
     */
    @Test
    void shouldReturnIfIsEmpty(){
        //Given
        HashSet set = new HashSet();
        //Then
        assertThat(set.isEmpty()).isTrue();
        //When
        set.add(1);
        //Then
        assertThat(set.isEmpty()).isFalse();
    }
    /**
     * Test method size.
     */
    @Test
    void shouldReturnTheSize() {
        //Given
        HashSet set = new HashSet();
        //Then
        assertThat(set.size()).isEqualTo(0);
        assertThat(set.isEmpty()).isTrue();
        //When
        set.add(1);
        //Then
        assertThat(set.size()).isEqualTo(1);
        assertThat(set.isEmpty()).isFalse();
        //When
        set.add(1);
        //Then
        assertThat(set.size()).isEqualTo(1);
        assertThat(set.isEmpty()).isFalse();
        //When
        set.add(2);
        //Then
        assertThat(set.size()).isEqualTo(2);
        assertThat(set.isEmpty()).isFalse();
        //When
        set.remove(1);
        //Then
        assertThat(set.size()).isEqualTo(1);
        assertThat(set.isEmpty()).isFalse();
    }
    /**
     * Test method contain.
     */
    @Test
    void shouldReturnIfElementContains(){
        //Given
        HashSet set = new HashSet();
        //When
        set.add(1);
        //Then
        assertThat(set.contains(1)).isTrue();
        assertThat(set.contains(2)).isFalse();
    }

    /**
     * Test method add.
     */
    @Test
    void shouldTestIfElementExistAlready() {
        //Given
        HashSet set = new HashSet();
        //When
        set.add(1);
        //Then
        assertThat(set.add(1)).isFalse();
        assertThat(set.add(2)).isTrue();
    }

    /**
     * Test method remove.
     */
    @Test
    void shouldTestIfElementExist() {
        //Given
        HashSet set = new HashSet();
        //When
        set.add(1);
        //Then
        assertThat(set.remove(1)).isTrue();
        assertThat(set.remove(1)).isFalse();
    }

    /**
     * Test method clear.
     */
    @Test
    void shouldBeEmptyAfterClear() {
        //Given
        HashSet set = new HashSet();
        //When
        set.add(1);
        //Then
        assertThat(set.isEmpty()).isFalse();
        //When
        set.clear();
        //Then
        assertThat(set.isEmpty()).isTrue();
    }

    /**
     * Test method addAll.
     */
    @Test
    void shouldAddAll() {
        //Given
        HashSet set = new HashSet();
        //When
        set.addAll(Arrays.asList(0, 1, 2, 3));
        //Then
        assertThat(set.contains(0)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.contains(2)).isTrue();
        assertThat(set.contains(3)).isTrue();
        assertThat(set.size()).isEqualTo(4);
    }
    /**
     * Test method removeAll.
     */
    @Test
    void shouldRemoveAll() {
        //Given
        HashSet set = new HashSet();
        //When
        set.addAll(Arrays.asList(0, 1, 2, 3));
        //Then
        assertThat(set.contains(1)).isTrue();
        assertThat(set.size()).isEqualTo(4);
        //When
        set.removeAll(Arrays.asList(0, 1, 2));
        //Then
        assertThat(set.contains(1)).isFalse();
        assertThat(set.contains(3)).isTrue();
        assertThat(set.size()).isEqualTo(1);
    }

    /**
     * Test method clone.
     */
    @Test
    void shouldClone() {
        HashSet set = new HashSet(Arrays.asList(0, 1, 2, 3));
        Object copy = set.clone();
        assertThat(set).isEqualTo(copy);
    }
}
