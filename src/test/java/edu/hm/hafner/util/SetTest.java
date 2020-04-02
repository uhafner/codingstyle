package edu.hm.hafner.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Verifies that {@code HashSet<Integer>} objects comply with the contract in {@link HashSet} and {@link Set}.
 *
 * @author Marc Hennig
 */
public class SetTest {
    @Test
    void shouldHandleInvalidConstruction() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> new HashSet<Integer>(null));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet<Integer>(-1));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet<Integer>(1, 0));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet<Integer>(1, -1));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new HashSet<Integer>(-1, 0.5f));
    }

    @Test
    void shouldBeEmptyNewSet() {
        // Given
        Set<Integer> sut = new HashSet<>();

        // Then
        assertThat(sut.isEmpty()).isTrue();
        assertThat(sut).isEmpty();
        assertThat(sut.size()).isZero();
        assertThat(sut).size().isZero();
    }

    @Test
    void shouldNotBeEmptyAfterAdd() {
        // Given
        Set<Integer> sut = new HashSet<>();

        // When
        assertThat(sut.add(1)).isTrue();
        assertThat(sut.add(2)).isTrue();

        // Then
        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut).isNotEmpty();
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut).size().isEqualTo(2);
    }

    @Test
    void shouldNotBeEmptyAfterAddAll() {
        // Given
        Set<Integer> sut = new HashSet<>();

        // When
        sut.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

        // Then
        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.size()).isEqualTo(10);
    }


    @Test
    void shouldContainElementsOnce() {
        // Given
        Integer[] elements = new Integer[] {0, 1, 2, 3, 4};
        Set<Integer> sut = new HashSet<>();

        // When
        assertThat(sut.addAll(Arrays.asList(elements))).isTrue();
        assertThat(sut.addAll(Arrays.asList(elements))).isFalse();
        assertThat(sut.add(5)).isTrue();
        assertThat(sut.add(5)).isFalse();

        // Then
        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.size()).isEqualTo(6);
        assertThat(sut).containsExactlyInAnyOrder(0, 1, 2, 3, 4, 5);
        assertThat(sut.contains(0)).isTrue();
        assertThat(sut.contains(1)).isTrue();
        assertThat(sut.contains(2)).isTrue();
        assertThat(sut.contains(3)).isTrue();
        assertThat(sut.contains(4)).isTrue();
        assertThat(sut.contains(5)).isTrue();
    }

    @Test
    void shouldNotContainRemovedElements() {
        // Given
        Integer[] elements = new Integer[] {0, 1, 2, 3, 4};
        Set<Integer> sut = new HashSet<>(Arrays.asList(elements));

        // When
        assertThat(sut.remove(0)).isTrue();
        assertThat(sut.remove(0)).isFalse();
        assertThat(sut.removeAll(Arrays.asList(1, 2))).isTrue();
        assertThat(sut.removeAll(Arrays.asList(1, 2))).isFalse();

        // Then
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut).size().isEqualTo(2);
        assertThat(sut.contains(0)).isFalse();
        assertThat(sut.contains(1)).isFalse();
        assertThat(sut.contains(2)).isFalse();
        assertThat(sut).doesNotContain(0, 1, 2);
    }

    @Test
    void shouldRetainElements() {
        // Given
        Integer[] elements = new Integer[] {0, 1, 2, 3, 4};
        Set<Integer> sut = new HashSet<>(Arrays.asList(elements));

        // When
        assertThat(sut.retainAll(Arrays.asList(3, 4))).isTrue();
        assertThat(sut.retainAll(Arrays.asList(3, 4))).isFalse();

        // Then
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut).size().isEqualTo(2);
        assertThat(sut.contains(0)).isFalse();
        assertThat(sut.contains(1)).isFalse();
        assertThat(sut.contains(2)).isFalse();
        assertThat(sut).containsExactlyInAnyOrder(3, 4);
    }

    @Test
    void shouldBeEmptyAfterClear() {
        // Given
        Integer[] elements = new Integer[] {0, 1, 2, 3, 4};
        Set<Integer> sut = new HashSet<>(Arrays.asList(elements));

        // When
        sut.add(5);
        sut.clear();

        // Then
        assertThat(sut.isEmpty()).isTrue();
        assertThat(sut).isEmpty();
        assertThat(sut.size()).isZero();
        assertThat(sut).size().isZero();
    }

    @Test
    void shouldConvertToArray() {
        // Given
        Integer[] elements = new Integer[] {0, 1, 2, 3, 4};
        Set<Integer> sut = new HashSet<>(Arrays.asList(elements));

        // When
        Integer[] arr = sut.toArray(new Integer[0]);

        // Then
        assertThat(arr).isNotNull();
        assertThat(sut).containsExactlyInAnyOrder(arr);
    }
}
