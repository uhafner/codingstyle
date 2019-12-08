package edu.hm.hafner.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Defines several architecture rules that should be enforced in this project.
 *
 * @author Ullrich Hafner
 */
public final class ArchitectureRules {
    /** Junit 5 test classes should not be public. */
    public static final ArchRule NO_PUBLIC_TEST_CLASSES =
            noClasses().that().haveSimpleNameEndingWith("Test")
                    .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                    .should().bePublic();

    /**
     * Methods or constructors that are annotated with {@link VisibleForTesting} must not be called by other classes.
     * These methods are meant to be {@code private}. Only test classes are allowed to call these methods.
     */
    public static final ArchRule NO_TEST_API_CALLED =
            noClasses().that().haveSimpleNameNotEndingWith("Test")
                    .should().callCodeUnitWhere(new AccessRestrictedToTests());

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_PACKAGE_ACCESSED =
            noClasses()
            .should().dependOnClassesThat(resideInAnyPackage(
                    "org.apache.commons.lang..",
                    "org.joda.time..",
                    "javax.xml.bind..",
                    "javax.annotation.."));

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_CLASSES_CALLED
            = noClasses()
            .should().callCodeUnitWhere(new TargetIsForbiddenClass(
                    "org.junit.jupiter.api.Assertions", "org.junit.Assert"));

    /**
     * Matches if a call from outside the defining class uses a method or constructor annotated with {@link
     * VisibleForTesting}. There are two exceptions:
     * <ul>
     * <li>The method is called on the same class</li>
     * <li>The method is called in a method also annotated with {@link VisibleForTesting}</li>
     * </ul>
     */
    private static class AccessRestrictedToTests extends DescribedPredicate<JavaCall<?>> {
        AccessRestrictedToTests() {
            super("access is restricted to tests");
        }

        @Override
        public boolean apply(final JavaCall<?> input) {
            return isVisibleForTesting(input.getTarget())
                    && !input.getOriginOwner().equals(input.getTargetOwner())
                    && !isVisibleForTesting(input.getOrigin());
        }

        private boolean isVisibleForTesting(final CanBeAnnotated target) {
            return target.isAnnotatedWith(VisibleForTesting.class);
        }
    }

    /**
     * Matches if a code unit of one of the registered classes has been called.
     */
    private static class TargetIsForbiddenClass extends DescribedPredicate<JavaCall<?>> {
        private final String[] classes;

        TargetIsForbiddenClass(final String... classes) {
            super("forbidden class");

            this.classes = Arrays.copyOf(classes, classes.length);
        }

        @Override
        public boolean apply(final JavaCall<?> input) {
            return StringUtils.containsAny(input.getTargetOwner().getFullName(), classes)
                    && !input.getName().equals("assertTimeoutPreemptively");
        }
    }

    private ArchitectureRules() {
        // prevents instantiation
    }
}
