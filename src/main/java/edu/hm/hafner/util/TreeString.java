package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * {@link TreeString} is an alternative string representation that saves the memory when you have a large number of
 * strings that share common prefixes (such as various file names.)
 * <p>
 * {@link TreeString} can be built with {@link TreeStringBuilder}.
 *
 * @author Kohsuke Kawaguchi
 */
public final class TreeString implements Serializable {
    private static final long serialVersionUID = 3621959682117480904L;

    /** Parent node that represents the prefix. */
    @CheckForNull
    private TreeString parent;

    /** {@link #parent} + {@code label} is the string value of this node. */
    private char[] label;

    /**
     * Creates a new root {@link TreeString}.
     */
    TreeString() {
        this(null, "");
    }

    /**
     * Creates a new {@link TreeString} with the given parent and suffix.
     *
     * @param parent
     *         the parent
     * @param label
     *         the suffix
     */
    @SuppressWarnings("NullAway")
    TreeString(@CheckForNull final TreeString parent, final String label) {
        Ensure.that(parent == null || !label.isEmpty())
                .isTrue("if there's a parent '%s', label '%s' can't be empty", parent, label);

        this.parent = parent;
        this.label = label.toCharArray(); // string created as a substring of another string can have a lot of garbage attached to it.
    }

    String getLabel() {
        return new String(label);
    }

    /**
     * Inserts a new node between this node and its parent, and returns the newly inserted node.
     * <p>
     * This operation doesn't change the string representation of this node.
     *
     * @param prefix
     *         the prefix to remove
     *
     * @return the new node in the middle
     */
    TreeString split(final String prefix) {
        Ensure.that(getLabel().startsWith(prefix)).isTrue();

        char[] suffix = new char[label.length - prefix.length()];
        System.arraycopy(label, prefix.length(), suffix, 0, suffix.length);

        TreeString middle = new TreeString(parent, prefix);
        label = suffix;
        parent = middle;

        return middle;
    }

    @VisibleForTesting
    @CheckForNull
    TreeString getParent() {
        return parent;
    }

    /**
     * How many nodes do we have from the root to this node (including 'this' itself?). Thus depth of the root node is
     * 1.
     *
     * @return the depth
     */
    private int depth() {
        int i = 0;
        for (TreeString p = this; p != null; p = p.parent) {
            i++;
        }
        return i;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeString that = (TreeString) o;
        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Returns the full string representation.
     */
    @Override
    public String toString() {
        char[][] tokens = new char[depth()][];
        int i = tokens.length;
        int sz = 0;
        for (TreeString p = this; p != null; p = p.parent) {
            tokens[--i] = p.label;
            sz += p.label.length;
        }

        StringBuilder buf = new StringBuilder(sz);
        for (char[] token : tokens) {
            buf.append(token);
        }

        return buf.toString();
    }

    /**
     * Interns {@link #label}.
     *
     * @param table
     *         the table containing the existing strings
     */
    void dedup(final Map<String, char[]> table) {
        String l = getLabel();
        char[] v = table.get(l);
        if (v == null) {
            table.put(l, label);
        }
        else {
            label = v;
        }
    }

    public boolean isBlank() {
        return StringUtils.isBlank(toString());
    }

    /**
     * Creates a {@link TreeString}. Useful if you need to create one-off {@link TreeString} without {@link
     * TreeStringBuilder}. Memory consumption is still about the same to {@code new String(string)}.
     *
     * @param string
     *         the tree string
     *
     * @return the new {@link TreeString}
     */
    public static TreeString valueOf(final String string) {
        return new TreeString(null, string);
    }
}
