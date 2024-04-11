package edu.hm.hafner.util;

import java.util.List;

import org.junit.jupiter.api.Disabled;

/**
 * Shows that the JDK does know nothing about Liskov substitution principle.
 *
 * @author Ullrich Hafner
 */
@Disabled("This test is only for demonstration purposes")
class LspViolatedTest extends LspTest {
    @Override
    protected List<String> createList() {
        return List.of("LSP");
    }
}
