package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.ConstructorCallTarget;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructorCall;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Defines several architecture rules that should be enforced in this project.
 *
 * @author Ullrich Hafner
 */
public final class ArchitectureRules {
    /** Never create exception without any context. */
    public static final ArchRule NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR =
            noClasses().that().haveSimpleNameNotContaining("Benchmark")
                    .should().callConstructorWhere(new ExceptionHasNoContext());

    /** Junit 5 test classes should not be public. */
    public static final ArchRule NO_PUBLIC_TEST_CLASSES =
            noClasses().that().haveSimpleNameEndingWith("Test")
                    .and().haveSimpleNameNotContaining("_jmh")
                    .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                    .should().bePublic();

    /** Junit 5 test methods should not be public. */
    public static final ArchRule ONLY_PACKAGE_PRIVATE_TEST_METHODS =
            methods().that().areAnnotatedWith(Test.class)
                    .or().areAnnotatedWith(ParameterizedTest.class)
                    .and().areDeclaredInClassesThat()
                    .haveSimpleNameEndingWith("Test")
                    .should().bePackagePrivate();

    /** ArchUnit tests should not be public. */
    public static final ArchRule NO_PUBLIC_ARCHITECTURE_TESTS =
            fields().that().areAnnotatedWith(ArchTest.class)
                    .should().notBePublic();

    /**
     * Methods or constructors that are annotated with {@link VisibleForTesting} must not be called by other classes.
     * These methods are meant to be {@code private}. Only test classes are allowed to call these methods.
     */
    public static final ArchRule NO_TEST_API_CALLED =
            noClasses().that()
                    .haveSimpleNameNotEndingWith("Test").and().haveSimpleNameNotContaining("Benchmark")
                    .should().callCodeUnitWhere(new AccessRestrictedToTests());

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_PACKAGE_ACCESSED =
            noClasses().should().dependOnClassesThat(resideInAnyPackage(
                    "org.apache.commons.lang..",
                    "org.joda.time..",
                    "javax.xml.bind..",
                    "net.jcip.annotations..",
                    "javax.annotation..",
                    "junit..",
                    "org.hamcrest..",
                    "com.google.common..",
                    "org.junit"
            ));

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_ANNOTATION_USED =
            noClasses().should().dependOnClassesThat().haveSimpleNameEndingWith("Nullable");

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_CLASSES_CALLED
            = noClasses()
            .should().callCodeUnitWhere(new TargetIsForbiddenClass(
                    "org.junit.jupiter.api.Assertions", "org.junit.Assert"));

    /** Ensures that the {@code readResolve} methods are protected so subclasses can call the parent method. */
    public static final ArchRule READ_RESOLVE_SHOULD_BE_PROTECTED =
            methods().that().haveName("readResolve").and().haveRawReturnType(Object.class)
                    .should().beDeclaredInClassesThat().implement(Serializable.class)
                    .andShould().beProtected().allowEmptyShould(true);

    private ArchitectureRules() {
        // prevents instantiation
    }

    /**
     * Matches if a call from outside the defining class uses a method or constructor annotated with
     * {@link VisibleForTesting}. There are two exceptions:
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
                    && !"assertTimeoutPreemptively".equals(input.getName());
        }
    }

    /**
     * Predicate to match exception constructor calls without contexts.
     */
    public static class ExceptionHasNoContext extends DescribedPredicate<JavaConstructorCall> {
        private final List<Class<? extends Throwable>> allowedExceptions;

        /**
         * Creates a new predicate.
         *
         * @param allowedExceptions
         *         exceptions that are allowed to be instantiated without arguments
         */
        @SafeVarargs
        public ExceptionHasNoContext(final Class<? extends Throwable>... allowedExceptions) {
            super("exception context is missing");

            this.allowedExceptions = Arrays.asList(allowedExceptions);
        }

        @Override
        public boolean apply(final JavaConstructorCall javaConstructorCall) {
            ConstructorCallTarget target = javaConstructorCall.getTarget();
            if (target.getRawParameterTypes().size() > 0) {
                return false;
            }
            return target.getOwner().isAssignableTo(Throwable.class)
                    && !isPermittedException(target.getOwner());
        }

        private boolean isPermittedException(final JavaClass owner) {
            return allowedExceptions.stream().anyMatch(owner::isAssignableTo);
        }
    }
}
