package edu.hm.hafner.util.internal;

import edu.hm.hafner.util.Option;

public class OtherMain {
    public static void main(final String[] args) {
        var option = new Option("key", "value");

        var key = option.getKey();
    }
}
