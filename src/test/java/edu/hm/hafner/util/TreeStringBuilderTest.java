package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static edu.hm.hafner.util.assertions.Assertions.*;

/**
 * Tests the class {@link TreeStringBuilder}.
 *
 * @author Kohsuke Kawaguchi
 * @author Ullrich Hafner
 */
class TreeStringBuilderTest {
    /** Creates several tree strings and checks that new tree strings will use the prefix of a previous one. */
    @Test
    @SuppressWarnings({"ConstantConditions", "NullAway"})
    void shouldCreateSimpleTreeStringsWithBuilder() {
        var builder = new TreeStringBuilder();
        var foo = builder.intern("foo");
        assertThat(foo).hasToString("foo").isNotBlank();
        assertThat(foo.getLabel()).isEqualTo("foo");

        var treeString = builder.intern("foo/bar/zot");
        assertThat(treeString).hasToString("foo/bar/zot");
        assertThat(treeString.getLabel()).isEqualTo("/bar/zot");
        assertThat(treeString.getParent()).isSameAs(foo);

        var interned = builder.intern(treeString);
        assertThat(interned).hasToString("foo/bar/zot");
        assertThat(interned.getLabel()).isEqualTo("/bar/zot");

        assertThat(treeString).isSameAs(interned);

        assertThat(builder.intern("")).hasToString("");

        var otherMiddleChild = builder.intern("foo/bar/xxx");
        assertThat(otherMiddleChild).hasToString("foo/bar/xxx");
        assertThat(otherMiddleChild.getLabel()).isEqualTo("xxx");

        var parent = otherMiddleChild.getParent();
        assertThat(parent).isSameAs(treeString.getParent());
        assertThat(Objects.requireNonNull(parent.getParent())).isSameAs(foo);

        // the middle node changed label but not toString
        assertThat(treeString.getLabel()).isEqualTo("zot");
        assertThat(treeString).hasToString("foo/bar/zot");

        assertThat(builder.intern("")).isBlank();
        assertThat(TreeString.valueOf("foo/bar/zot")).hasToString("foo/bar/zot");
    }

    @Test
    void shouldProvideProperEqualsAndHashCode() {
        var builder = new TreeStringBuilder();

        var foo = builder.intern("foo");
        var bar = builder.intern("foo/bar");
        var zot = builder.intern("foo/bar/zot");

        assertThat(new TreeStringBuilder().intern("foo")).isEqualTo(foo);
        assertThat(new TreeStringBuilder().intern("foo/bar")).isEqualTo(bar);
        assertThat(new TreeStringBuilder().intern("foo/bar/zot")).isEqualTo(zot);

        assertThat(new TreeStringBuilder().intern("foo")).hasSameHashCodeAs(foo);
        assertThat(new TreeStringBuilder().intern("foo/bar")).hasSameHashCodeAs(bar);
        assertThat(new TreeStringBuilder().intern("foo/bar/zot")).hasSameHashCodeAs(zot);
    }

    @Test
    void shouldThrowAssertionErrorIfLabelIsEmpty() {
        assertThatThrownBy(() -> new TreeString(new TreeString(), "")).isInstanceOf(AssertionError.class);
    }

    /**
     * Pseudo random (but deterministic) test.
     */
    @Test
    void shouldCreateRandomTreeStrings() {
        String[] dictionary = {"aa", "b", "aba", "ba"};
        var builder = new TreeStringBuilder();

        var random = new Random(0);

        List<String> a = new ArrayList<>();
        List<TreeString> o = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            var b = new StringBuilder();
            for (int j = 0; j < random.nextInt(10) + 3; j++) {
                b.append(dictionary[random.nextInt(4)]);
            }
            var s = b.toString();

            a.add(s);

            var p = builder.intern(s);
            assertThat(p).hasToString(s);
            o.add(p);
        }

        // make sure values are still all intact
        for (int i = 0; i < a.size(); i++) {
            assertThat(o.get(i)).hasToString(a.get(i));
        }

        builder.dedup();

        // verify one more time
        for (int i = 0; i < a.size(); i++) {
            assertThat(o.get(i)).hasToString(a.get(i));
        }
    }

    @Test
    void shouldObeyEqualsContract() {
        EqualsVerifier.forClass(TreeString.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(TreeString.class, TreeString.valueOf("Red"), TreeString.valueOf("Blue"))
                .verify();
    }
}
