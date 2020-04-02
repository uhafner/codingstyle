package edu.hm.hafner.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SetTest {
    HashSet<Integer> s;

    @BeforeEach
    public void setUp() {
        s = new HashSet<Integer>();
    }

    @Test
    void testAdd() {
        Assertions.assertEquals(true, s.add(Integer.valueOf(9)));
        Assertions.assertEquals(1, s.size());
        Assertions.assertNotEquals(true, s.add(Integer.valueOf(9)));
    }

    @Test
    void testSize() {
        s.add(Integer.valueOf(9));
        Assertions.assertEquals(1, s.size());
    }

    @Test
    void testIsEmpty() {
        Assertions.assertTrue(s.isEmpty());
        s.add(Integer.valueOf(9));
        Assertions.assertFalse(s.isEmpty());
    }

    @Test
    void testClear() {
        s.add(Integer.valueOf(9));
        s.clear();
        Assertions.assertTrue(s.isEmpty());
    }

    @Test
    void testRemove() {
        s.add(Integer.valueOf(9));
        Assertions.assertTrue(s.remove(Integer.valueOf(9)));
        Assertions.assertFalse(s.remove(Integer.valueOf(9)));
    }

    @Test
    void testRemoveAll() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));

        s.addAll(list);
        Assertions.assertTrue(s.removeAll(list));
        Assertions.assertTrue(s.isEmpty());
    }

    @Test
    void testAddAll() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));

        s.addAll(list);
        Assertions.assertFalse(s.isEmpty());
        Assertions.assertEquals(2, s.size());
    }

    @Test
    void testContains() {
        Assertions.assertTrue(s.isEmpty());

        s.add(Integer.valueOf(9));
        Assertions.assertTrue(s.contains(Integer.valueOf(9)));
    }

    @Test
    void testContainsAll() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));

        s.addAll(list);
        Assertions.assertTrue(s.containsAll(list));
    }

    @Test
    void testEquals() {
        s.add(Integer.valueOf(1));
        HashSet<Integer> s2 = new HashSet<Integer>();
        s2.add(Integer.valueOf(1));
        Assertions.assertTrue(s.equals(s2));
    }

    @Test
    void testHashCode() {
        Assertions.assertNotNull(s.hashCode());
        Assertions.assertEquals(0, s.hashCode());
        s.add(Integer.valueOf(1));
        Assertions.assertEquals(Integer.valueOf(1).hashCode(), s.hashCode());
    }

    @Test
    void testIterator() {
        s.add(Integer.valueOf(1));

        Iterator iterator = s.iterator();
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals(Integer.valueOf(1), iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    void testRetainAll() {
        s.add(Integer.valueOf(1));
        s.add(Integer.valueOf(2));
        HashSet<Integer> s2 = new HashSet<Integer>();
        s2.add(Integer.valueOf(1));
        Assertions.assertFalse(s.equals(s2));
        s.retainAll(s2);
        Assertions.assertFalse(s.contains(Integer.valueOf(2)));
    }

    @Test
    void testToArray() {
        s.add(Integer.valueOf(1));

        Object[] arr = s.toArray();
        Assertions.assertEquals(1, arr.length);
        Assertions.assertEquals(Integer.valueOf(1), arr[0]);
    }

    @Test
    void testToArrayType() {
        s.add(Integer.valueOf(1));

        Integer[] arr = new Integer[1];
        s.toArray(arr);
        Assertions.assertEquals(1, arr.length);
        Assertions.assertEquals(Integer.valueOf(1), arr[0]);
    }

}
