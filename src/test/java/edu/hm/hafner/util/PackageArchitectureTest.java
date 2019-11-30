package edu.hm.hafner.util;

import java.net.URL;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.Configurations.*;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.*;

/**
 * Checks the package architecture of this plugin.
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("hideutilityclassconstructor")
@AnalyzeClasses(packages = "edu.hm.hafner..", importOptions = DoNotIncludeTests.class)
class PackageArchitectureTest {
    private static final URL PACKAGE_DESIGN = PackageArchitectureTest.class.getResource("/design.puml");

    @ArchTest
    static final ArchRule ADHERES_TO_PACKAGE_DESIGN
            = classes().should(adhereToPlantUmlDiagram(PACKAGE_DESIGN,
            consideringOnlyDependenciesInAnyPackage("edu.hm.hafner..")));

    @ArchTest
    static final ArchRule NO_PUBLIC_TEST_CLASSES = ArchitectureRules.NO_PUBLIC_TEST_CLASSES;

    @ArchTest
    static final ArchRule NO_TEST_API_CALLED = ArchitectureRules.NO_TEST_API_CALLED;

    @ArchTest
    static final ArchRule NO_FORBIDDEN_PACKAGE_ACCESSED = ArchitectureRules.NO_FORBIDDEN_PACKAGE_ACCESSED;

    @ArchTest
    static final ArchRule NO_FORBIDDEN_CLASSES_CALLED = ArchitectureRules.NO_FORBIDDEN_CLASSES_CALLED;

}
