package edu.hm.hafner.util;

import java.io.Serializable;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.ConstructorCallTarget;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructorCall;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.core.domain.JavaAccess.Predicates.*;
import static com.tngtech.archunit.lang.conditions.ArchConditions.*;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Defines several architecture rules that should be enforced in this project.
 *
 * @author Ullrich Hafner
 */
public final class ArchitectureRules {
    /** Tests should not use fields. Recommendation is to use factory methods for stubs and mocks. */
    public static final ArchRule NO_FIELDS_IN_TESTS =
            fields().that().areDeclaredInClassesThat().haveSimpleNameEndingWith("Test")
                    .should().beFinal().andShould().haveModifier(JavaModifier.STATIC)
                    .because("use factory methods in favor of instance fields when creating stubs or mocks in tests")
                    .allowEmptyShould(true);

    /** Never create exception without any context. */
    public static final ArchRule NO_EXCEPTIONS_WITH_NO_ARG_CONSTRUCTOR =
            noClasses().that().haveSimpleNameNotContaining("Benchmark")
                    .should().callConstructorWhere(exceptionHasNoContextAsParameter())
                    .because("exceptions should include failure-capture information in detail messages (Effective Java Item 75)")
                    .allowEmptyShould(true);

    /** Junit 5 test classes should not be public. */
    public static final ArchRule NO_PUBLIC_TEST_CLASSES =
            noClasses().that().haveSimpleNameEndingWith("Test")
                    .and().haveSimpleNameNotContaining("_jmh")
                    .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                    .should().bePublic()
                    .because("test classes are not part of the API and should be hidden in a package");

    /** Junit 5 test methods should not be public. */
    public static final ArchRule ONLY_PACKAGE_PRIVATE_TEST_METHODS =
            methods().that().areAnnotatedWith(Test.class)
                    .or().areAnnotatedWith(ParameterizedTest.class)
                    .and().areDeclaredInClassesThat()
                    .haveSimpleNameEndingWith("Test")
                    .should().bePackagePrivate()
                    .because("test methods are not part of the API and should be hidden in a package");

    /** ArchUnit tests should not be public. */
    public static final ArchRule ONLY_PACKAGE_PRIVATE_ARCHITECTURE_TESTS =
            fields().that().areAnnotatedWith(ArchTest.class)
                    .should().bePackagePrivate()
                    .because("architecture tests are not part of the API and should be hidden in a package")
                    .allowEmptyShould(true);

    /**
     * Methods or constructors that are annotated with {@link VisibleForTesting} must not be called by other classes.
     * These methods are meant to be {@code private}. Only test classes are allowed to call these methods.
     */
    public static final ArchRule NO_TEST_API_CALLED =
            noClasses().that().haveSimpleNameNotEndingWith("Test")
                    .and().haveSimpleNameNotContaining("Benchmark")
                    .should().callCodeUnitWhere(accessIsRestrictedForTests())
                    .because("Production code should never access methods that are marked with @VisibleForTesting")
                    .allowEmptyShould(true);

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_PACKAGE_ACCESSED =
            noClasses().should().dependOnClassesThat().resideInAnyPackage(
                    "org.apache.commons.lang..",
                    "org.joda.time..",
                    "javax.xml.bind..",
                    "net.jcip.annotations..",
                    "junit..",
                    "org.hamcrest..",
                    "com.google.common..",
                    "org.junit"
            );

    /** Prevents that classes use visible but forbidden annotations. */
    public static final ArchRule NO_FORBIDDEN_ANNOTATION_USED =
            noClasses().should()
                    .dependOnClassesThat()
                    .haveNameMatching("javax.annotation.Check.*")
                    .orShould()
                    .dependOnClassesThat()
                    .haveNameMatching("javax.annotation.Nonnull")
                    .orShould()
                    .dependOnClassesThat()
                    .haveNameMatching("javax.annotation.Nullable")
                    .orShould()
                    .dependOnClassesThat()
                    .haveNameMatching("javax.annotation.Parameters.*")
                    .orShould()
                    .dependOnClassesThat()
                    .haveNameMatching(
                            "edu.umd.cs.findbugs.annotations.Nullable") // only CheckForNull and NonNull is allowed
                    .because("JSR 305 annotations are now part of edu.umd.cs.findbugs.annotations package");

    /** Prevents that classes use visible but forbidden API. */
    public static final ArchRule NO_FORBIDDEN_CLASSES_CALLED =
            noClasses().should().callCodeUnitWhere(targetOwner(has(
                            fullyQualifiedName("org.junit.jupiter.api.Assertions")
                                    .or(fullyQualifiedName("org.junit.Assert")))))
                    .because("only AssertJ should be used for assertions");

    /** Ensures that the {@code readResolve} methods are protected so subclasses can call the parent method. */
    public static final ArchRule READ_RESOLVE_SHOULD_BE_PROTECTED =
            methods().that().haveName("readResolve").and().haveRawReturnType(Object.class)
                    .should().beDeclaredInClassesThat().implement(Serializable.class)
                    .andShould(beProtected()).allowEmptyShould(true);

    private static ExceptionHasNoContext exceptionHasNoContextAsParameter() {
        return new ExceptionHasNoContext();
    }

    private static DescribedPredicate<? super JavaCall<?>> accessIsRestrictedForTests() {
        return new AccessRestrictedToTests();
    }

    private ArchitectureRules() {
        // prevents instantiation
    }

    private static ArchCondition<JavaMethod> beProtected() {
        return new ShouldBeProtectedCondition();
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
        public boolean test(final JavaCall<?> input) {
            return isVisibleForTesting(input.getTarget())
                    && !input.getOriginOwner().equals(input.getTargetOwner())
                    && !isVisibleForTesting(input.getOrigin());
        }

        private boolean isVisibleForTesting(final CanBeAnnotated target) {
            return target.isAnnotatedWith(VisibleForTesting.class);
        }
    }

    /**
     * Matches if an exception has no context, i.e., the constructor is invoked without a message.
     */
    private static class ExceptionHasNoContext extends DescribedPredicate<JavaConstructorCall> {
        private final List<Class<? extends Throwable>> allowedExceptions;

        /**
         * Creates a new predicate.
         *
         * @param allowedExceptions
         *         exceptions that are allowed to be instantiated without arguments
         */
        @SafeVarargs
        ExceptionHasNoContext(final Class<? extends Throwable>... allowedExceptions) {
            super("exception context is missing");

            this.allowedExceptions = List.of(allowedExceptions);
        }

        @Override
        public boolean test(final JavaConstructorCall javaConstructorCall) {
            ConstructorCallTarget target = javaConstructorCall.getTarget();
            if (!target.getRawParameterTypes().isEmpty()) {
                return false;
            }
            return target.getOwner().isAssignableTo(Throwable.class)
                    && !isPermittedException(target.getOwner());
        }

        private boolean isPermittedException(final JavaClass owner) {
            return allowedExceptions.stream().anyMatch(owner::isAssignableTo);
        }
    }

    private static class ShouldBeProtectedCondition extends ArchCondition<JavaMethod> {
        ShouldBeProtectedCondition() {
            super("should be protected");
        }

        @Override
        public void check(final JavaMethod method, final ConditionEvents events) {
            if (method.getModifiers().contains(JavaModifier.PROTECTED)) {
                return;
            }
            if (method.getOwner().getModifiers().contains(JavaModifier.FINAL)) {
                return;
            }
            events.add(SimpleConditionEvent.violated(method,
                    String.format("%s is not protected but the class might be extended in %s",
                            method.getDescription(), method.getSourceCodeLocation())));
        }
    }
}
