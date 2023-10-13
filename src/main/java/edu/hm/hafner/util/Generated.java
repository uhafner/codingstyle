package edu.hm.hafner.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * This annotation is used to mark source code that has been generated or is somehow not relevant for style checking or
 * code coverage analysis. It is quite similar to the {@code javax.annotation.Generated} annotation. The
 * main difference is that it has class retention on so is available for tools that work on bytecode (like JaCoCo,
 * PIT, or SpotBugs).
 */
@Retention(CLASS)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, METHOD, CONSTRUCTOR, FIELD,
        LOCAL_VARIABLE, PARAMETER})
public @interface Generated {
    /**
     * An optional property that identifies the code generator.
     *
     * @return the name of the generator
     */
    String[] value() default "";
}
