package edu.hm.hafner.util;

import edu.hm.hafner.util.PackageDetector.FileSystemFacade;

/**
 * Factory to create package detectors.
 *
 * @author Ullrich Hafner
 */
public final class PackageDetectorFactory {
    /**
     * Creates a new package detector runner that uses the detectors for Java, Kotlin, and C#.
     *
     * @return the package detector runner
     */
    public static PackageDetectorRunner createPackageDetectors() {
        return createPackageDetectors(new FileSystemFacade());
    }

    @VisibleForTesting
    static PackageDetectorRunner createPackageDetectors(final FileSystemFacade facade) {
        return new PackageDetectorRunner(
                new JavaPackageDetector(facade),
                new KotlinPackageDetector(facade),
                new CSharpNamespaceDetector(facade));
    }

    private PackageDetectorFactory() {
        // prevents instantiation
    }
}
