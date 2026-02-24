package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.hm.hafner.util.PackageDetectorFactory.FileSystemFacade;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link PackageDetectorRunner}.
 *
 * @author Ullrich Hafner
 */
class PackageDetectorRunnerTest extends ResourceTest {
    @ParameterizedTest(name = "{index} => file={0}, expected package={1}")
    @CsvSource({
            "MavenJavaTest.txt.java, hudson.plugins.tasks.util",
            "ActionBinding.cs, Avaloq.SmartClient.Utilities",
            "KotlinTest.txt.kt, edu.hm.kersting"})
    void shouldExtractPackageNames(final String fileName, final String expectedPackage) throws IOException {
        assertThat(detect(fileName)).contains(expectedPackage);
    }

    @Test
    void shouldNotExtractPackageNamesWhenSuffixDoesNotMatch() throws IOException {
        assertThat(detect("Package.txt")).isEmpty();
    }

    @ParameterizedTest(name = "{index} => file={0}, no package found")
    @ValueSource(strings = {"MavenJavaTest.txt", "relative.txt", "KotlinTest.txt"})
    void shouldNotAcceptFile(final String fileName) throws IOException {
        assertThat(detect(fileName)).isEmpty();
    }

    private Optional<String> detect(final String fileName) throws IOException {
        try (var stream = asInputStream(fileName)) {
            var fileSystem = mock(FileSystemFacade.class);
            when(fileSystem.openFile(fileName)).thenReturn(stream);

            var detectors = PackageDetectorFactory.createPackageDetectors(fileSystem);

            return detectors.detectPackageName(fileName, StandardCharsets.UTF_8);
        }
    }

    @Test
    void shouldHandleException() throws IOException {
        var fileSystem = mock(FileSystemFacade.class);
        when(fileSystem.openFile(anyString())).thenThrow(new IOException("Simulated"));

        assertThat(PackageDetectorFactory.createPackageDetectors(fileSystem)
                .detectPackageName("file.java", StandardCharsets.UTF_8)).isEmpty();
    }
}
