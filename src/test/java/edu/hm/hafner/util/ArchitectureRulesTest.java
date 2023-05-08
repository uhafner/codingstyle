package edu.hm.hafner.util;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static org.assertj.core.api.Assertions.*;

/**
 * Verifies the architecture rules in {@link ArchitectureRules}.
 *
 * @author Ullrich Hafner
 */
class ArchitectureRulesTest {
    private static final String BROKEN_CLASS_NAME = ArchitectureRulesViolatedTest.class.getTypeName();

    @Test
    void shouldUseProtectedForReadResolve() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.READ_RESOLVE_SHOULD_BE_PROTECTED.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "was violated (3 times)",
                        "Method <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesAlsoViolatedTest.readResolve()> is not protected but the class might be extended in (ArchitectureRulesTest.java:",
                        "Method <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.readResolve()> is not declared in classes that implement java.io.Serializable in (ArchitectureRulesTest.java:",
                        "Method <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.readResolve()> is not protected but the class might be extended in (ArchitectureRulesTest.java:");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.READ_RESOLVE_SHOULD_BE_PROTECTED.check(importPassingClass()));
    }

    @Test
    void shouldNotUseJsr305Annotations() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.NO_FORBIDDEN_ANNOTATION_USED.check(
                                importClasses(ArchitectureRulesViolatedTest.class)))
                .withMessageContainingAll("was violated (4 times)", "edu.umd.cs.findbugs.annotations",
                        "Field <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.empty> is annotated with <javax.annotation.Nonnull>",
                        "Field <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.noNullable> is annotated with <edu.umd.cs.findbugs.annotations.Nullable>",
                        "Method <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.method(java.lang.String)> is annotated with <javax.annotation.CheckForNull>",
                        "Parameter <java.lang.String> of method <edu.hm.hafner.util.ArchitectureRulesTest$ArchitectureRulesViolatedTest.method(java.lang.String)> is annotated with <javax.annotation.Nonnull>");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_FORBIDDEN_ANNOTATION_USED.check(importPassingClass()));
    }

    @Test
    void shouldVerifyThatTestsDoNotUseFields() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.NO_FIELDS_IN_TESTS.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "use factory methods in favor of instance fields when creating stubs or mocks in tests");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_FIELDS_IN_TESTS.check(importPassingClass()));
    }

    @Test
    void shouldVerifyForbiddenAnnotations() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.NO_FORBIDDEN_CLASSES_CALLED.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "only AssertJ should be used");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_FORBIDDEN_CLASSES_CALLED.check(importPassingClass()));
    }

    @Test
    void shouldVerifyExceptionWithNoArgConstructorCalled() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "(Effective Java Item 75)");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR.check(importPassingClass()));
    }

    @Test
    void shouldVerifyNoPublicTestClassesRule() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.NO_PUBLIC_TEST_CLASSES.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "test classes are not part of the API");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_PUBLIC_TEST_CLASSES.check(importPassingClass()));
    }

    @Test
    void shouldVerifyNoPublicTestMethodsRule() {
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                        () -> ArchitectureRules.ONLY_PACKAGE_PRIVATE_TEST_METHODS.check(importBrokenClass()))
                .withMessageContainingAll(BROKEN_CLASS_NAME, "test methods are not part of the API");

        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.ONLY_PACKAGE_PRIVATE_TEST_METHODS.check(importPassingClass()));
    }

    private JavaClasses importPassingClass() {
        return new ClassFileImporter().importClasses(ArchitectureRulesPassedTest.class,
                ArchitectureRulesAlsoPassedTest.class);
    }

    private JavaClasses importBrokenClass() {
        return importClasses(ArchitectureRulesViolatedTest.class,
                ArchitectureRulesAlsoViolatedTest.class);
    }

    private JavaClasses importClasses(final Class<?>... classes) {
        return new ClassFileImporter().importClasses(classes);
    }

    @SuppressWarnings("all") @Generated // This class is just there to be used in architecture tests
    public static class ArchitectureRulesViolatedTest {
        @javax.annotation.Nonnull
        private final String empty = "";
        @edu.umd.cs.findbugs.annotations.Nullable
        private final String noNullable = null;

        @Test
        public void shouldFail() {
            org.junit.jupiter.api.Assertions.assertEquals(1, 1);

            throw new IllegalArgumentException();
        }

        @javax.annotation.CheckForNull
        protected String method(@javax.annotation.Nonnull String param) {
            return null;
        }

        /**
         * Called after de-serialization to retain backward compatibility.
         *
         * @return this
         */
        private Object readResolve() {
            return this;
        }
    }

    @SuppressWarnings("all") @Generated // This class is just there to be used in architecture tests
    @SuppressFBWarnings("SE")
    public static class ArchitectureRulesAlsoViolatedTest implements Serializable {

        /**
         * Called after de-serialization to retain backward compatibility.
         *
         * @return this
         */
        private Object readResolve() {
            return this;
        }
    }

    @SuppressWarnings("all") @Generated // This class is just there to be used in architecture tests
    @SuppressFBWarnings("SE")
    static final class ArchitectureRulesPassedTest implements Serializable {
        @Test
        void shouldPass() {
            throw new IllegalArgumentException("context");
        }

        /**
         * Called after de-serialization to retain backward compatibility.
         *
         * @return this
         */
        private Object readResolve() {
            return this;
        }
    }

    @SuppressWarnings("all") // This class is just there to be used in architecture tests
    @SuppressFBWarnings("SE")
    static class ArchitectureRulesAlsoPassedTest implements Serializable {
        /**
         * Called after de-serialization to retain backward compatibility.
         *
         * @return this
         */
        protected Object readResolve() {
            return this;
        }
    }
}
