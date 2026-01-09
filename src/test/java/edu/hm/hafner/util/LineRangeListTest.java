package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link LineRangeList}.
 *
 * @author Kohsuke Kawaguchi
 * @author Ullrich Hafner
 */
class LineRangeListTest {
    @Test
    void shouldStoreBigValues() {
        var list = new LineRangeList();
        var range = new LineRange(1350, Integer.MAX_VALUE);
        list.add(range);
        assertThat(list).containsExactly(range);
    }

    @Test
    void shouldStoreRangeWithOneLines() {
        var list = new LineRangeList();
        var range = new LineRange(0, 0);
        list.add(range);
        assertThat(list).containsExactly(range);
    }

    @Test
    void shouldStoreRangeWithTwoLines() {
        var list = new LineRangeList();
        var range = new LineRange(128, 129);
        list.add(range);
        assertThat(list).containsExactly(range);
    }

    @Test
    void shouldSupportSetOperations() {
        var list = new LineRangeList();
        var range = new LineRange(1, 2);
        list.add(range);

        assertThat(list.getFirst()).isEqualTo(range);
        assertThat(list.getFirst()).isNotSameAs(range);
        assertThat(list).hasSize(1);

        var other = new LineRange(3, 4);
        assertThat(list.set(0, other)).isEqualTo(range);
        assertThat(list.getFirst()).isEqualTo(other);
        assertThat(list.getFirst()).isNotSameAs(other);
        assertThat(list).hasSize(1);

        assertThat(list.removeFirst()).isEqualTo(other);
        assertThat(list).hasSize(0);
    }

    /** Tests the internal buffer resize operation. */
    @Test
    void shouldResizeCorrectly() {
        var list = new LineRangeList();
        for (int i = 0; i < 100; i++) {
            list.add(new LineRange(i * 2, i * 2 + 1));
        }
        list.trim();
        assertThat(list).hasSize(100);

        for (int i = 0; i < 100; i++) {
            assertThat(list.get(i)).isEqualTo(new LineRange(i * 2, i * 2 + 1));
            assertThat(list.contains(new LineRange(i * 2, i * 2 + 1))).isTrue();
        }

        assertThat(list).hasSize(100);
    }

    @Test
    void shouldProvideContains() {
        var last = createThreeElements();
        last.remove(new LineRange(4, 5));
        assertThat(last).containsExactly(new LineRange(0, 1), new LineRange(2, 3));

        var middle = createThreeElements();
        middle.remove(new LineRange(2, 3));
        assertThat(middle).containsExactly(new LineRange(0, 1), new LineRange(4, 5));

        var first = createThreeElements();
        assertThat(first).contains(new LineRange(0, 1));
        assertThat(first).contains(new LineRange(2, 3));
        assertThat(first).contains(new LineRange(4, 5));

        first.remove(new LineRange(0, 1));
        assertThat(first).containsExactly(new LineRange(2, 3), new LineRange(4, 5));

        assertThat(first).contains(new LineRange(2, 3));
        assertThat(first).doesNotContain(new LineRange(0, 1));
    }

    private List<LineRange> createThreeElements() {
        var range = new LineRangeList();
        range.add(new LineRange(0, 1));
        range.add(new LineRange(2, 3));
        range.add(new LineRange(4, 5));
        assertThat(range).containsExactly(new LineRange(0, 1), new LineRange(2, 3), new LineRange(4, 5));
        return range;
    }
}
