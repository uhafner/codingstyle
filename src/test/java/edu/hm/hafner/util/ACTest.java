package edu.hm.hafner.util;

public abstract class ACTest<T extends Comparable<T>> {
    void shouldBeNegativeIfThisIsSmaller() {
        T smaller = createSmallerSut();
        T greater = createGreaterSut();
    }

    /**
     * Creates a subject under test. The SUT must be smaller than the SUT of the opposite method {@link
     * #createGreaterSut()}.
     *
     * @return the SUT
     */
    protected abstract T createSmallerSut();

    /**
     * Creates a subject under test. The SUT must be greater than the SUT of the opposite method {@link
     * #createSmallerSut()}.
     *
     * @return the SUT
     */
    protected abstract T createGreaterSut();
}
