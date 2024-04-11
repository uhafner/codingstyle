package edu.hm.hafner.util;

import java.io.IOException;

/**
 * Creates a serialization file for an {@link Option} instance.
 *
 * @author Ullrich Hafner
 */
final class OptionWriter {
    /**
     * Serializes an issue to a file. Use this method in case the issue properties have been changed and the readResolve
     * method has been adapted accordingly so that the old serialization still can be read.
     *
     * @param args
     *         not used
     *
     * @throws IOException
     *         if the file could not be written
     */
    public static void main(final String... args) throws IOException {
        new OptionTest().createSerializationFile();
    }

    private OptionWriter() {
        // prevents instantiation
    }
}
