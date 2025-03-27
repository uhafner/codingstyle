package edu.hm.hafner.util.internal;

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
public final class Option implements Serializable {
    @Serial
    private static final long serialVersionUID = -6416888680799872630L;

    private final String key;
    private final String value;

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
}
