package edu.hm.hafner.util;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Detects package or namespace names of files in the file system.
 *
 * @author Ullrich Hafner
 */
public class PackageDetectorRunner {
    private final List<PackageDetector> detectors;

    PackageDetectorRunner(final PackageDetector... detectors) {
        this.detectors = Arrays.asList(detectors);
    }

    /**
     * Detects the package name of the specified file based on several detector strategies.
     *
     * @param fileName
     *         the filename of the file to scan
     * @param charset
     *         the charset to use when reading the source files
     *
     * @return the detected package name or {@link Optional#empty()} if no package name could be detected
     */
    public Optional<String> detectPackageName(final String fileName, final Charset charset) {
        return detectors.stream()
                .filter(detector -> detector.accepts(fileName))
                .map(detector -> detector.detectPackageName(fileName, charset))
                .flatMap(Optional::stream)
                .findFirst();
    }
}
