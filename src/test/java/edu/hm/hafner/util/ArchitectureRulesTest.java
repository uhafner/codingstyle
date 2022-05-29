package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import static org.assertj.core.api.Assertions.*;

/**
 * Verifies the architecture rules in {@link ArchitectureRules}.
 *
 * @author Ullrich Hafner
 */
class ArchitectureRulesTest {
    @Test
    void shouldVerifyNoPublicTestClassesRule() {
        JavaClasses violatedClasses = importClasses(NoPublicTestElementsViolatedTest.class);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                () -> ArchitectureRules.NO_PUBLIC_TEST_CLASSES.check(violatedClasses));
        JavaClasses passedClasses = new ClassFileImporter().importClasses(NoPublicTestElementsPassedTest.class);
        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.NO_PUBLIC_TEST_CLASSES.check(passedClasses));
    }

    @Test
    void shouldVerifyNoPublicTestMethodsRule() {
        JavaClasses violatedClasses = importClasses(NoPublicTestElementsViolatedTest.class);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(
                () -> ArchitectureRules.ONLY_PACKAGE_PRIVATE_TEST_METHODS.check(violatedClasses));
        JavaClasses passedClasses = new ClassFileImporter().importClasses(NoPublicTestElementsPassedTest.class);
        assertThatNoException().isThrownBy(
                () -> ArchitectureRules.ONLY_PACKAGE_PRIVATE_TEST_METHODS.check(passedClasses));
    }

    private JavaClasses importClasses(final Class<?>... classes) {
        return new ClassFileImporter().importClasses(classes);
    }

    public static class NoPublicTestElementsViolatedTest {
        @Test
        public void shouldFail() {
        }
    }

    static class NoPublicTestElementsPassedTest {
        @Test
        void shouldPass() {
        }
    }
}
