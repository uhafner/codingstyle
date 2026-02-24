package edu.hm.hafner.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation for repeating the {@link SuppressMutation} annotation.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface SuppressMutations {
    /**
     * The array of {@link SuppressMutation} annotations.
     *
     * @return an array of suppression rules
     */
    SuppressMutation[] value();
}
