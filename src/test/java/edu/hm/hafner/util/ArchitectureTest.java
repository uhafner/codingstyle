package edu.hm.hafner.util;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import edu.hm.hafner.util.ArchitectureTest.DoNotIncludeRulesUnderTest;

/**
 * Checks the architecture of this module.
 *
 * @author Ullrich Hafner
 */
@AnalyzeClasses(packages = "edu.hm.hafner", importOptions = DoNotIncludeRulesUnderTest.class)
class ArchitectureTest {
    @ArchTest
    static final ArchRule NO_PUBLIC_TEST_CLASSES = ArchitectureRules.NO_PUBLIC_TEST_CLASSES;

    @ArchTest
    static final ArchRule ONLY_PACKAGE_PRIVATE_TEST_METHODS = ArchitectureRules.ONLY_PACKAGE_PRIVATE_TEST_METHODS;

    @ArchTest
    static final ArchRule ONLY_PACKAGE_PRIVATE_ARCHITECTURE_TESTS = ArchitectureRules.ONLY_PACKAGE_PRIVATE_ARCHITECTURE_TESTS;

    @ArchTest
    static final ArchRule NO_FIELDS_IN_TESTS = ArchitectureRules.NO_FIELDS_IN_TESTS;

    @ArchTest
    static final ArchRule NO_TEST_API_CALLED = ArchitectureRules.NO_TEST_API_CALLED;

    @ArchTest
    static final ArchRule NO_FORBIDDEN_PACKAGE_ACCESSED = ArchitectureRules.NO_FORBIDDEN_PACKAGE_ACCESSED;

    @ArchTest
    static final ArchRule NO_FORBIDDEN_CLASSES_CALLED = ArchitectureRules.NO_FORBIDDEN_CLASSES_CALLED;

    @ArchTest
    static final ArchRule NO_FORBIDDEN_ANNOTATION_USED = ArchitectureRules.NO_FORBIDDEN_ANNOTATION_USED;

    @ArchTest
    static final ArchRule NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR = ArchitectureRules.NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR;

    static final class DoNotIncludeRulesUnderTest implements ImportOption {
        @Override
        public boolean includes(final Location location) {
            return !location.contains(ArchitectureRulesTest.class.getSimpleName());
        }
    }
}
