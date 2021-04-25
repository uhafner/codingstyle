package edu.hm.hafner.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.input.BOMInputStream;
import org.opentest4j.TestAbortedException;

import com.google.errorprone.annotations.MustBeClosed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static org.assertj.core.api.Assumptions.*;

/**
 * Base class for tests that need to read resource files from disk. Provides several useful methods that simplify
 * reading of resources from disk.
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class ResourceTest {
    /**
     * Returns whether the OS under test is Windows or Unix.
     *
     * @return {@code true} if the OS is Windows, {@code false} otherwise
     */
    protected boolean isWindows() {
        return File.pathSeparatorChar == ';';
    }

    /**
     * Creates an empty file in the default temporary-file directory, using
     * the prefix and suffix "test" to generate its name. The resulting {@code
     * Path} is associated with the default {@code FileSystem}.
     *
     * @return  the path to the newly created file that did not exist before
     *          this method was invoked
     */
    protected Path createTempFile() {
        try {
            return Files.createTempFile("test", ".test");
        }
        catch (IOException | IllegalArgumentException | UnsupportedOperationException | SecurityException exception) {
            throw new AssertionError(exception);
        }
    }

    /**
     * Reads all the bytes from a file. The method ensures that the file is closed when all bytes have been read or an
     * I/O error, or other runtime exception, is thrown.
     *
     * <p> Note that this method is intended for simple cases where it is
     * convenient to read all bytes into a byte array. It is not intended for reading in large files.
     * </p>
     *
     * @param fileName
     *         name of the desired resource
     *
     * @return the content represented by a byte array
     */
    protected byte[] readAllBytes(final String fileName) {
        try {
            return readAllBytes(getPath(fileName));
        }
        catch (URISyntaxException e) {
            throw new AssertionError("Can't find resource " + fileName, e);
        }
    }

    /**
     * Reads all the bytes from a file. The method ensures that the file is closed when all bytes have been read or an
     * I/O error, or other runtime exception, is thrown.
     *
     * <p> Note that this method is intended for simple cases where it is
     * convenient to read all bytes into a byte array. It is not intended for reading in large files.
     * </p>
     *
     * @param path
     *         path of the desired resource
     *
     * @return the content represented by a byte array
     */
    protected byte[] readAllBytes(final Path path) {
        try {
            return Files.readAllBytes(path);
        }
        catch (IOException e) {
            throw new AssertionError("Can't read resource " + path, e);
        }
    }

    @SuppressFBWarnings("UI_INHERITANCE_UNSAFE_GETRESOURCE")
    private Path getPath(final String name) throws URISyntaxException {
        URL resource = getTestResourceClass().getResource(name);
        ensureThatResourceExists(resource, name);
        return Paths.get(resource.toURI());
    }

    /**
     * Read all lines from the desired resource as a {@code Stream}, i.e. this method populates lazily as the stream is
     * consumed.
     * <p>
     * Bytes from the resource are decoded into characters using UTF-8 and the same line terminators as specified by
     * {@link Files#readAllLines(Path, Charset)} are supported.
     * </p>
     *
     * @param fileName
     *         name of the desired resource
     *
     * @return the content represented as a {@link Stream} of lines
     */
    @MustBeClosed
    protected Stream<String> asStream(final String fileName) {
        return asStream(fileName, StandardCharsets.UTF_8);
    }

    /**
     * Read all lines from the desired resource as a {@code Stream}, i.e. this method populates lazily as the stream is
     * consumed.
     * <p>
     * Bytes from the resource are decoded into characters using the specified charset and the same line terminators as
     * specified by {@link Files#readAllLines(Path, Charset)} are supported.
     * </p>
     *
     * @param fileName
     *         name of the desired resource
     * @param charset
     *         the charset to use for decoding
     *
     * @return the content represented as a {@link Stream} of lines
     */
    @MustBeClosed
    protected Stream<String> asStream(final String fileName, final Charset charset) {
        try {
            return Files.lines(getPath(fileName), charset);
        }
        catch (IOException | URISyntaxException e) {
            throw new AssertionError("Can't read resource " + fileName, e);
        }
    }

    /**
     * Finds a resource with the given name and returns an input stream with UTF-8 decoding.
     *
     * @param fileName
     *         name of the desired resource
     *
     * @return the content represented as an {@link InputStream}
     */
    protected InputStream asInputStream(final String fileName) {
        InputStream stream = getTestResourceClass().getResourceAsStream(fileName);

        ensureThatResourceExists(stream, fileName);

        return stream;
    }

    private void ensureThatResourceExists(final Object resource, final String fileName) {
        if (resource == null) {
            throw new AssertionError("Can't find resource " + fileName);
        }
    }

    /**
     * Returns the class that should be used to read the resource files of a test.
     *
     * @return default value is the actual test class
     */
    protected Class<?> getTestResourceClass() {
        return getClass();
    }

    /**
     * Finds a resource with the given name and returns the content (decoded with UTF-8) as String.
     *
     * @param fileName
     *         name of the desired resource
     *
     * @return the content represented as {@link String}
     */
    protected String toString(final String fileName) {
        return createString(readAllBytes(fileName));
    }

    /**
     * Returns the content of the specified {@link Path} (decoded with UTF-8) as String.
     *
     * @param file
     *         the desired file
     *
     * @return the content represented as {@link String}
     */
    protected String toString(final Path file) {
        return createString(readAllBytes(file));
    }

    private String createString(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Read all lines from the specified text String as a {@code Stream}.
     *
     * @param text
     *         the text to return as {@link Stream} of lines
     *
     * @return the content represented by a byte array
     */
    @SuppressWarnings({"resource", "IOResourceOpenedButNotSafelyClosed"})
    protected Stream<String> getTextLinesAsStream(final String text) {
        return new BufferedReader(new StringReader(text)).lines();
    }

    /**
     * Returns the {@link Path} of the specified resource. The file name  must be relative to the test class.
     *
     * @param fileName
     *         the file to read (relative to this {@link ResourceTest} class)
     *
     * @return an {@link BOMInputStream input stream} using character set UTF-8
     * @see #getTestResourceClass()
     */
    protected Path getResourceAsFile(final String fileName) {
        try {
            URL resource = getTestResourceClass().getResource(fileName);

            ensureThatResourceExists(resource, fileName);

            return Paths.get(resource.toURI());
        }
        catch (URISyntaxException e) {
            throw new AssertionError("Can't open file " + fileName, e);
        }
    }

    /**
     * Assumes that the test is running on Windows.
     *
     * @throws TestAbortedException if the test is running on a Unix system
     */
    protected void assumeThatTestIsRunningOnWindows() {
        assumeThat(isWindows()).as("Test is not running on Windows").isTrue();
    }

    /**
     * Assumes that the test is running on Windows.
     *
     * @throws TestAbortedException if the test is running on a Unix system
     */
    protected void assumeThatTestIsRunningOnUnix() {
        assumeThat(isWindows()).as("Test is not running on Unix").isFalse();
    }
}
