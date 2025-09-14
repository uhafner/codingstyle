package edu.hm.hafner.util;

import edu.hm.hafner.util.PackageDetectorFactory.FileSystemFacade;

import java.util.regex.Pattern;

/**
 * Detects the namespace of a C# workspace file.
 *
 * @author Ullrich Hafner
 */
class CSharpNamespaceDetector extends PackageDetector {
    private static final Pattern NAMESPACE_PATTERN = Pattern.compile("^\\s*namespace\\s+([^{]*)\\s*\\{?\\s*$");

    CSharpNamespaceDetector(final FileSystemFacade fileSystem) {
        super(fileSystem);
    }

    @Override
    public boolean accepts(final String fileName) {
        return fileName.endsWith(".cs");
    }

    @Override
    Pattern getPattern() {
        return NAMESPACE_PATTERN;
    }
}
