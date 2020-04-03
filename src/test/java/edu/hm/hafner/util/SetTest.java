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
     * Test the method size and isEmpty.
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
}
