package edu.hm.hafner.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link ResourceExtractor}.
 *
 * @author Ullrich Hafner
 */
class ResourceExtractorTest {
    private static final String ASSERTJ_TEMPLATES = "assertj-templates/has_assertion_template.txt";
    private static final String JENKINS_FILE = "Jenkinsfile.reference";
    private static final String MANIFEST_MF = "META-INF/MANIFEST.MF";

    @Test
    void shouldLocateResourcesInFolder() {
        var folderExtractor = new ResourceExtractor(ResourceExtractor.class);
        assertThat(folderExtractor.isReadingFromJarFile()).isFalse();
        assertThat(normalizePath(folderExtractor.getResourcePath())).endsWith("target/classes");
    }

    private String normalizePath(final String resourcePath) {
        return new PathUtil().getAbsolutePath(resourcePath);
    }

    @Test
    void shouldLocateResourcesInJarFile() {
        var jarExtractor = new ResourceExtractor(StringUtils.class);
        assertThat(jarExtractor.isReadingFromJarFile()).isTrue();
        assertThat(jarExtractor.getResourcePath()).matches(".*commons-lang3-\\d+\\.\\d+\\.\\d+.jar");
    }

    @Test
    void shouldExtractFromFolder(@TempDir final Path targetFolder) {
        var proxy = new ResourceExtractor(ResourceExtractor.class);

        proxy.extract(targetFolder, ASSERTJ_TEMPLATES, JENKINS_FILE,
                "edu/hm/hafner/util/ResourceExtractor.class");

        assertThat(readToString(targetFolder.resolve(ASSERTJ_TEMPLATES)))
                .contains("has${Property}(${propertyType} ${property_safe})");
        assertThat(readToString(targetFolder.resolve(JENKINS_FILE)))
                .contains("node", "stage ('Build and Static Analysis')");
    }

    @Test
    void shouldThrowExceptionIfTargetIsFileInFolder() throws IOException {
        var proxy = new ResourceExtractor(ResourceExtractor.class);

        Path tempFile = Files.createTempFile("tmp", "tmp");
        assertThatIllegalArgumentException().isThrownBy(() -> proxy.extract(tempFile, MANIFEST_MF));
    }

    @Test
    void shouldThrowExceptionIfFileDoesNotExistInFolder(@TempDir final Path targetFolder) {
        var proxy = new ResourceExtractor(ResourceExtractor.class);

        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() ->
                proxy.extract(targetFolder, "does-not-exist"));
    }

    @Test
    void shouldExtractFromJar(@TempDir final Path targetFolder) {
        var proxy = new ResourceExtractor(StringUtils.class);

        proxy.extract(targetFolder, MANIFEST_MF,
                "org/apache/commons/lang3/StringUtils.class");

        assertThat(readToString(targetFolder.resolve(MANIFEST_MF))).contains("Manifest-Version: 1.0",
                "Bundle-SymbolicName: org.apache.commons.lang3");
    }

    @Test
    void shouldThrowExceptionIfTargetIsFileInJar() throws IOException {
        var proxy = new ResourceExtractor(StringUtils.class);

        Path tempFile = Files.createTempFile("tmp", "tmp");
        assertThatIllegalArgumentException().isThrownBy(() -> proxy.extract(tempFile, MANIFEST_MF));
    }

    @Test
    void shouldThrowExceptionIfFileDoesNotExistInJar(@TempDir final Path targetFolder) {
        var proxy = new ResourceExtractor(StringUtils.class);

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() ->
                proxy.extract(targetFolder, "does-not-exist"));
    }

    @Test
    void shouldHandleClassloaderProblems() {
        ProtectionDomain protectionDomain = mock(ProtectionDomain.class);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ResourceExtractor(ResourceExtractor.class, protectionDomain))
                .withMessageContainingAll("CodeSource for", "ResourceExtractor");

        CodeSource codeSource = mock(CodeSource.class);
        when(protectionDomain.getCodeSource()).thenReturn(codeSource);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ResourceExtractor(ResourceExtractor.class, protectionDomain))
                .withMessageContaining("CodeSource location for", "ResourceExtractor");

        URL url = mock(URL.class);
        when(codeSource.getLocation()).thenReturn(url);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ResourceExtractor(ResourceExtractor.class, protectionDomain))
                .withMessageContaining("CodeSource location path", "ResourceExtractor");

        when(url.getPath()).thenReturn("file.jar");
        var extractor = new ResourceExtractor(ResourceExtractor.class, protectionDomain);

        assertThat(extractor.getResourcePath()).isEqualTo("file.jar");
    }

    private String readToString(final Path output) {
        try {
            return new String(Files.readAllBytes(output), StandardCharsets.UTF_8);
        }
        catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
