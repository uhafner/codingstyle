package edu.hm.hafner.util;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * A proxy for resources. Extracts a given collection of files from the classpath and copies them to a target path.
 *
 * @author Ullrich Hafner
 */
public class ResourceExtractor {
    private final boolean readingFromJarFile;
    private final Extractor extractor;
    private final String resourcePath;

    /**
     * Creates a new {@link ResourceExtractor} that extracts resources from the classloader of the specified class.
     *
     * @param targetClass
     *         the target class to use the classloader from
     */
    public ResourceExtractor(final Class<?> targetClass) {
        this(targetClass, targetClass.getProtectionDomain());
    }

    @VisibleForTesting
    ResourceExtractor(final Class<?> targetClass, final ProtectionDomain protectionDomain) {
        var codeSource = protectionDomain.getCodeSource();
        if (codeSource == null) {
            throw new IllegalArgumentException("There is no CodeSource for " + targetClass);
        }
        var location = codeSource.getLocation();
        if (location == null) {
            throw new IllegalArgumentException("There is no CodeSource location for " + targetClass);
        }
        var locationPath = location.getPath();
        if (StringUtils.isBlank(locationPath)) {
            throw new IllegalArgumentException("The CodeSource location path is not set for " + targetClass);
        }
        var entryPoint = new File(locationPath).toPath();
        readingFromJarFile = Files.isRegularFile(entryPoint);
        if (readingFromJarFile) {
            extractor = new JarExtractor(entryPoint);
        }
        else {
            extractor = new FolderExtractor(entryPoint);
        }
        resourcePath = entryPoint.toString();
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public boolean isReadingFromJarFile() {
        return readingFromJarFile;
    }

    /**
     * Extracts the specified source files from the classloader and saves them to the specified target folder.
     *
     * @param targetDirectory
     *         the target path that will be the parent folder of all extracted files
     * @param source
     *         the source file to extract
     * @param sources
     *         the additional source files to extract
     */
    public void extract(final Path targetDirectory, final String source, final String... sources) {
        if (!Files.isDirectory(targetDirectory)) {
            throw new IllegalArgumentException(
                    "Target directory must be an existing directory: " + targetDirectory); // implement
        }
        var allSources = Arrays.copyOf(sources, sources.length + 1);
        allSources[sources.length] = source;
        extractor.extractFiles(targetDirectory, allSources);
    }

    /**
     * Extracts a collection of files and copies them to a given target path.
     */
    private abstract static class Extractor {
        private final Path entryPoint;

        Extractor(final Path entryPoint) {
            this.entryPoint = entryPoint;
        }

        Path getEntryPoint() {
            return entryPoint;
        }

        abstract void extractFiles(Path targetDirectory, String... sources);
    }

    /**
     * Extracts files from a folder, typically provided by the development environment or build system.
     */
    private static class FolderExtractor extends Extractor {
        FolderExtractor(final Path entryPoint) {
            super(entryPoint);
        }

        @Override
        public void extractFiles(final Path targetDirectory, final String... sources) {
            try {
                for (String source : sources) {
                    var targetFile = targetDirectory.resolve(source);
                    Files.createDirectories(targetFile);
                    copy(targetFile, source);
                }
            }
            catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        }

        private void copy(final Path target, final String source) {
            try {
                Files.copy(getEntryPoint().resolve(source), target, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        }
    }

    /**
     * Extracts files from a deployed jar file.
     */
    private static class JarExtractor extends Extractor {
        JarExtractor(final Path entryPoint) {
            super(entryPoint);
        }

        @Override
        public void extractFiles(final Path targetDirectory, final String... sources) {
            Set<String> remaining = Arrays.stream(sources).collect(Collectors.toSet());
            try (var jar = new JarFile(getEntryPoint().toFile())) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    var entry = entries.nextElement();
                    var name = entry.getName();
                    if (remaining.contains(name)) {
                        copy(targetDirectory, jar, entry, name);
                        remaining.remove(name);
                    }
                }
            }
            catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
            if (!remaining.isEmpty()) {
                throw new NoSuchElementException("The following files have not been found: " + remaining);
            }
        }

        private void copy(final Path targetDirectory, final JarFile jar, final JarEntry entry, final String name)
                throws IOException {
            var targetFile = targetDirectory.resolve(name);
            if (!targetFile.normalize().startsWith(targetDirectory)) {
                throw new IllegalArgumentException("Corrupt jar structure, contains invalid path: " + name);
            }
            var parent = targetFile.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (var inputStream = jar.getInputStream(entry); var outputStream = Files.newOutputStream(targetFile)) {
                IOUtils.copy(inputStream, outputStream);
            }
        }
    }
}
