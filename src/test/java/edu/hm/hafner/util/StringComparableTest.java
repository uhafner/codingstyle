package edu.hm.hafner.util;

/**
 * Example class that shows on how to verify that String instances comply with the contract in {@link
 * Comparable#compareTo(Object)}.
 *
 * @author Ullrich Hafner
 */
class StringComparableTest extends AbstractComparableTest<String> {
    @Override
    protected String createSmallerSut() {
        return "a bc";
    }

    @Override
    protected String createGreaterSut() {
        return "z yx";
    }
}
