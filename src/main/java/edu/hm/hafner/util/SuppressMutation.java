package edu.hm.hafner.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static edu.hm.hafner.util.PitMutator.*;

/**
 * Suppresses specific mutations when the feature {@code FANNOT} is enabled in PitMute.
 *
 * <p>
 * This annotation can be applied to classes or methods. When used without parameters, all mutations in that scope
 * are suppressed. For more information, please see the README in PitMute.
 * </p>
 *
 * @see <a href="https://github.com/uhafner/pitmute">PitMute</a>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SuppressMutations.class)
public @interface SuppressMutation {
    /**
     * Specifies the name of a mutator to be ignored.
     * If {@link #mutator()} is also provided, this value is ignored.
     *
     * @return the name of the mutator to suppress
     */
    String mutatorName() default "";

    /**
     * Limits the suppression to a specific line number.
     *
     * @return the line number to suppress, or -1 for all lines in the scope
     */
    int line() default -1;

    /**
     * Specifies a mutator to be ignored.
     * If both {@code mutator} and {@code mutatorName} are provided, {@code mutatorName} is ignored.
     *
     * @return the mutator to suppress
     */
    PitMutator mutator() default NONE;
}
