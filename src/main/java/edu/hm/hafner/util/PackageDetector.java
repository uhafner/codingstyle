package edu.hm.hafner.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.io.input.BOMInputStream;

import edu.hm.hafner.util.PackageDetectorFactory.FileSystemFacade;

/**
 * Base class for package detectors.
 *
 * @author Ullrich Hafner
 */
abstract class PackageDetector {
    private final FileSystemFacade fileSystem;

    /**
     * Creates a new instance of {@link PackageDetector}.
     *
     * @param fileSystem
     *         file system facade
     */
    PackageDetector(final FileSystemFacade fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Detects the package or namespace name of the specified file.
     *
     * @param fileName
     *         the file name of the file to scan
     * @param charset
     *         the charset to use when reading the source files
     *
     * @return the detected package or namespace name
     */
    public Optional<String> detectPackageName(final String fileName, final Charset charset) {
        try (var stream = fileSystem.openFile(fileName)) {
            return detectPackageName(stream, charset);
        }
        catch (IOException | InvalidPathException ignore) {
            // ignore IO errors
        }
        return Optional.empty();
    }

    private Optional<String> detectPackageName(final InputStream stream, final Charset charset) throws IOException {
        try (var buffer = new BufferedReader(
                new InputStreamReader(BOMInputStream.builder().setInputStream(stream).get(), charset))) {
            return detectPackageName(buffer.lines());
        }
    }

    /**
     * Detects the package or namespace name of the specified input stream. The stream will be closed automatically by
     * the caller of this method.
     *
     * @param lines
     *         the content of the file to scan
     *
     * @return the detected package or namespace name
     */
    private Optional<String> detectPackageName(final Stream<String> lines) {
        Pattern pattern = getPattern();
        return lines.map(pattern::matcher)
                .filter(Matcher::matches)
                .findFirst()
                .map(matcher -> matcher.group(1))
                .map(String::trim);
    }

    /**
     * Returns the Pattern for the Package Name in this kind of file.
     *
     * @return the Pattern.
     */
    abstract Pattern getPattern();

    /**
     * Returns whether this classifier accepts the specified file for processing.
     *
     * @param fileName
     *         the file name
     *
     * @return {@code true} if the classifier accepts the specified file for processing.
     */
    abstract boolean accepts(String fileName);
}
