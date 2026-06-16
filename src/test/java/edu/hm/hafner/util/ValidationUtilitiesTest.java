package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ValidationUtilitiesTest {
    @Test
    void shouldContainDefaultCharsets() {
        var allCharsets = getAllCharsets();
        assertThat(allCharsets).isNotEmpty().contains("UTF-8", "ISO-8859-1");
    }

    private String getAllCharsets() {
        return "UTF-8";
    }
}
