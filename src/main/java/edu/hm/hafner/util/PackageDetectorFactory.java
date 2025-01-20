package edu.hm.hafner.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import com.google.errorprone.annotations.MustBeClosed;

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

    /**
     * Creates a new package detector runner that uses the detectors for Java, Kotlin, and C#.
     *
     * @param facade
     *         the file system facade to use
     *
     * @return the package detector runner
     */
    @VisibleForTesting
    public static PackageDetectorRunner createPackageDetectors(final FileSystemFacade facade) {
        return new PackageDetectorRunner(
                new JavaPackageDetector(facade),
                new KotlinPackageDetector(facade),
                new CSharpNamespaceDetector(facade));
    }

    private PackageDetectorFactory() {
        // prevents instantiation
    }

    /**
     * Facade for file system operations. May be replaced by stubs in test cases.
     */
    @VisibleForTesting
    public static class FileSystemFacade {
        /**
         * Opens the specified file.
         *
         * @param fileName
         *         the name of the file to open
         *
         * @return the input stream to read the file
         * @throws IOException
         *         if the file could not be opened
         * @throws InvalidPathException
         *         the file name is invalid
         */
        @MustBeClosed
        public InputStream openFile(final String fileName) throws IOException, InvalidPathException {
            return Files.newInputStream(Paths.get(fileName));
        }
    }
}
