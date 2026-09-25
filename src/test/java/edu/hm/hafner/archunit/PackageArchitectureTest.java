package edu.hm.hafner.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.Configuration.consideringOnlyDependenciesInAnyPackage;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.adhereToPlantUmlDiagram;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.net.URL;
import java.util.Objects;

/**
 * Checks the package architecture of this module.
 *
 * @author Ullrich Hafner
 */
@AnalyzeClasses(packages = "edu.hm.hafner..", importOptions = DoNotIncludeTests.class)
final class PackageArchitectureTest {
    private static final URL PACKAGE_DESIGN = PackageArchitectureTest.class.getResource("/design.puml");

    @ArchTest
    static final ArchRule ADHERES_TO_PACKAGE_DESIGN = classes()
            .should(adhereToPlantUmlDiagram(
                    Objects.requireNonNull(PACKAGE_DESIGN),
                    consideringOnlyDependenciesInAnyPackage("edu.hm.hafner..")));
}
