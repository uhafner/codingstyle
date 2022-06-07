package edu.hm.hafner.util;

import java.util.Objects;

/**
 * Describes an incremental change of an observable collection.
 *
 * @param <E>
 *         type of the collection elements
 *
 * @author Ullrich Hafner
 */
public final class ChangeEvent<E> {
    private final E element;
    private final ChangeType type;

    /**
     * Creates a new change event.
     *
     * @param element
     *         the element that has been added, removed, or permutated.
     * @param type
     *         the type of the change
     */
    public ChangeEvent(final E element, final ChangeType type) {
        this.element = element;
        this.type = type;
    }

    public E getElement() {
        return element;
    }

    public ChangeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getElement(), getType());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChangeEvent<?> that = (ChangeEvent<?>) o;
        return Objects.equals(element, that.element) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, type);
    }

    /**
     * Type of the change event.
     */
    public enum ChangeType {
        /* The element has been added. */
        ADDITION,
        /* The element has been removed. */
        REMOVAL,
        /* The element has been permuted. */
        PERMUTATION
    }
}
