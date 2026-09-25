package edu.hm.hafner.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark source code that has been generated or is somehow not relevant for style checking or
 * code coverage analysis. It is quite similar to the annotation of the abandoned JSR305 project. The main difference is
 * that it has class retention, so it is available for tools that work on bytecode (like JaCoCo, PIT, or SpotBugs).
 */
@Retention(CLASS)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, METHOD, CONSTRUCTOR, FIELD, LOCAL_VARIABLE, PARAMETER})
public @interface Generated {
    /**
     * An optional property that identifies the code generator.
     *
     * @return the name of the generator
     */
    String[] value() default "";
}
