package edu.hm.hafner.util;

import java.util.Collections;
import java.util.HashSet;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link HashSet}.
 *
 * @author Dominik Moelter
 */
public class SetTest {

    HashSet createEmptyHashSet() {
        return new HashSet<Integer>();
    }

    HashSet createFilledHastSet(final Integer... values) {
        HashSet hashSet = new HashSet<Integer>();
        Collections.addAll(hashSet, values);
        return hashSet;
    }



}
