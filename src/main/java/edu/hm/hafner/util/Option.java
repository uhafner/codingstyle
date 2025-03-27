package edu.hm.hafner.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import com.google.errorprone.annotations.Immutable;

/**
 * An option is a key value pair.
 *
 * @author Ullrich Hafner
 */
@Immutable
public final class Option implements Serializable, Comparable<Option> {
    @Serial
    private static final long serialVersionUID = -1L;

    private final String key;
    private final String value;
    private final int wert = 0;

    /**
     * Creates an entry representing a mapping from the specified key to the specified value.
     *
     * @param key
     *         the key represented by this entry
     * @param value
     *         the value represented by this entry
     */
    public Option(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key corresponding to this entry.
     *
     * @return the key corresponding to this entry
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value corresponding to this entry.
     *
     * @return the value corresponding to this entry
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Option option = (Option) o;
        return Objects.equals(key, option.key) && Objects.equals(value, option.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Option{key='" + key + '\'' + ", value='" + value + '\'' + '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
     * {@code x.compareTo(y)} must throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param other
     *         the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     *         the specified object.
     * @throws NullPointerException
     *         if the specified object is null
     * @throws ClassCastException
     *         if the specified object's type prevents it from being compared to this object.
     */
    @Override
    public int compareTo(final Option other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare to null");
        }
        return getKey().compareTo(other.getKey());
    }
}
