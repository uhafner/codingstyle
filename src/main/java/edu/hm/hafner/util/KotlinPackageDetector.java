package edu.hm.hafner.util;

import java.util.regex.Pattern;

/**
 * Detects the package name of a Kotlin file.
 *
 * @author Bastian Kersting
 */
class KotlinPackageDetector extends PackageDetector {
    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "^\\s*package\\s*([a-z]+[.\\w]*)\\s*.*");

    @VisibleForTesting
    KotlinPackageDetector(final FileSystemFacade fileSystem) {
        super(fileSystem);
    }

    @Override
    Pattern getPattern() {
        return PACKAGE_PATTERN;
    }

    @Override
    boolean accepts(final String fileName) {
        return fileName.endsWith(".kt");
    }
}
