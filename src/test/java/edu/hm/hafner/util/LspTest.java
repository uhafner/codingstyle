package edu.hm.hafner.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Shows that the JDK does know nothing about Liskov substitution principle.
 *
 * @author Ullrich Hafner
 */
public class LspTest {
    @Test
    void shouldValidateLiskovSubstitutionPrinciple() {
        List<String> emptyList = createList();

        assertThat(emptyList).containsExactly("LSP");

        emptyList.clear();
        assertThat(emptyList).isEmpty();
    }

    protected List<String> createList() {
        var list = new ArrayList<String>();
        list.add("LSP");
        return list;
    }
}
