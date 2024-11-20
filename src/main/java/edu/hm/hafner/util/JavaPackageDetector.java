package edu.hm.hafner.util;

import java.util.regex.Pattern;

/**
 * Detects the package name of a Java file.
 *
 * @author Ullrich Hafner
 */
class JavaPackageDetector extends PackageDetector {
    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "^\\s*package\\s*([a-z]+[.\\w]*)\\s*;.*");

    JavaPackageDetector(final FileSystemFacade fileSystem) {
        super(fileSystem);
    }

    @Override
    Pattern getPattern() {
        return PACKAGE_PATTERN;
    }

    @Override
    public boolean accepts(final String fileName) {
        return fileName.endsWith(".java");
    }
}
