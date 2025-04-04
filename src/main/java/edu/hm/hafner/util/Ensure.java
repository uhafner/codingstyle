package edu.hm.hafner.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import com.google.errorprone.annotations.FormatMethod;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.CheckReturnValue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Provides several helper methods to validate method arguments and class invariants, thus supporting the design by
 * contract concept (DBC).
 *
 * <p>
 * Note: the static methods provided by this class use a fluent interface, i.e., in order to verify an assertion a
 * method sequence needs to be called.
 * </p>
 *
 * <p>
 *  Available checks:
 * </p>
 * <ul>
 *      <li>Boolean assertions, e.g., {@code Ensure.that(condition).isTrue(); } </li>
 *      <li>String assertions, e.g., {@code Ensure.that(string).isNotEmpty(); } </li>
 *      <li>Object assertions, e.g., {@code Ensure.that(element).isNotNull(); } </li>
 *      <li>Array assertions, e.g., {@code Ensure.that(array).isNotEmpty(); } </li>
 *      <li>Iterable assertions, e.g., {@code Ensure.that(collection).isNotNull(); } </li>
 * </ul>
 *
 * @author Ullrich Hafner
 * @see <a href="http://se.ethz.ch/~meyer/publications/computer/contract.pdf"> Design by Contract (Meyer, Bertrand)</a>
 */
public final class Ensure {
    /**
     * Returns a boolean condition.
     *
     * @param value
     *         the value to check
     *
     * @return a boolean condition
     */
    @CheckReturnValue
    public static BooleanCondition that(final boolean value) {
        return new BooleanCondition(value);
    }

    /**
     * Returns an object condition.
     *
     * @param value
     *         the value to check
     * @param additionalValues
     *         the additional values to check
     * @param <T>
     *         type to check
     *
     * @return an object condition
     */
    @CheckReturnValue
    public static <T> ObjectCondition<T> that(@CheckForNull final T value,
            @CheckForNull final Object... additionalValues) {
        return new ObjectCondition<>(value, additionalValues);
    }

    /**
     * Returns an iterable condition.
     *
     * @param value
     *         the value to check
     *
     * @return an iterable condition
     */
    @CheckReturnValue
    public static IterableCondition that(@CheckForNull final Iterable<?> value) {
        return new IterableCondition(value);
    }

    /**
     * Returns a collection condition.
     *
     * @param value
     *         the value to check
     *
     * @return a collection condition
     */
    @CheckReturnValue
    public static CollectionCondition that(@CheckForNull final Collection<?> value) {
        return new CollectionCondition(value);
    }

    /**
     * Returns an array condition.
     *
     * @param value
     *         the value to check
     *
     * @return an array condition
     */
    @SuppressWarnings({"PMD.UseVarargs", "AvoidObjectArrays"})
    @CheckReturnValue
    public static ArrayCondition that(@CheckForNull final Object[] value) {
        return new ArrayCondition(value);
    }

    /**
     * Returns a string condition.
     *
     * @param value
     *         the value to check
     *
     * @return a string condition
     */
    @CheckReturnValue
    public static StringCondition that(@CheckForNull final String value) {
        return new StringCondition(value);
    }

    /**
     * Returns an exception condition.
     *
     * @param value
     *         the value to check
     *
     * @return an exception condition
     */
    @CheckReturnValue
    public static ExceptionCondition that(@CheckForNull final Throwable value) {
        return new ExceptionCondition(value);
    }

    /**
     * Always throws an {@link AssertionError}.
     */
    public static void thatStatementIsNeverReached() {
        throwException("This statement should never be reached.");
    }

    /**
     * Always throws an {@link AssertionError}.
     *
     * @param explanation
     *         a {@link Formatter formatted message} explaining the assertion
     * @param args
     *         Arguments referenced by the format specifiers in the formatted explanation. If there are more arguments
     *         than format specifiers, the extra arguments are ignored. The number of arguments is variable and may be
     *         zero.
     */
    @FormatMethod
    public static void thatStatementIsNeverReached(final String explanation, final Object... args) {
        throwException(explanation, args);
    }

    /**
     * Throws an {@link AssertionError} with the specified detail message.
     *
     * @param message
     *         a {@link Formatter formatted message} with the description of the error
     * @param args
     *         Arguments referenced by the format specifiers in the formatted message. If there are more arguments than
     *         format specifiers, the extra arguments are ignored. The number of arguments is variable and may be zero.
     *
     * @throws AssertionError
     *         always thrown
     */
    @FormatMethod
    private static void throwException(final String message, @CheckForNull final Object... args) {
        throw new AssertionError(message.formatted(args));
    }

    /**
     * Throws a {@link NullPointerException} with the specified detail message.
     *
     * @param message
     *         a {@link Formatter formatted message} with the description of the error
     * @param args
     *         Arguments referenced by the format specifiers in the formatted message. If there are more arguments than
     *         format specifiers, the extra arguments are ignored. The number of arguments is variable and may be zero.
     *
     * @throws AssertionError
     *         always thrown
     */
    @FormatMethod
    private static void throwNullPointerException(final String message, final Object... args) {
        throw new NullPointerException(message.formatted(args)); // NOPMD
    }

    private Ensure() {
        // prevents instantiation
    }

    /**
     * Assertions for iterables.
     */
    public static class IterableCondition extends ObjectCondition<Iterable<?>> {
        /**
         * Creates a new instance of {@code IterableCondition}.
         *
         * @param value
         *         value of the condition
         */
        public IterableCondition(@CheckForNull final Iterable<?> value) {
            super(value);
        }

        /**
         * Ensures that the given iterable is not {@code null} and contains at least one element. Additionally, ensures
         * that each element of the iterable is not {@code null}.
         *
         * @throws AssertionError
         *         if the iterable is empty (or {@code null}), or at least one iterable element is {@code null}.
         */
        public void isNotEmpty() {
            isNotEmpty("Iterable is empty or NULL");
        }

        /**
         * Ensures that the given iterable is not {@code null} and contains at least one element. Additionally, ensures
         * that each element of the iterable is not {@code null}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the iterable is empty (or {@code null}), or at least one iterable element is {@code null}.
         */
        @FormatMethod
        public void isNotEmpty(final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (getValue().iterator().hasNext()) {
                for (Object object : getValue()) {
                    if (object == null) {
                        throwException(explanation, args);
                    }
                }
            }
            else {
                throwException(explanation, args);
            }
        }
    }

    /**
     * Assertions for iterables.
     */
    public static class CollectionCondition extends IterableCondition {
        /**
         * Creates a new instance of {@code CollectionCondition}.
         *
         * @param value
         *         value of the condition
         */
        public CollectionCondition(@CheckForNull final Collection<?> value) {
            super(value);
        }

        @Override
        Collection<?> getValue() {
            return (Collection<?>) super.getValue();
        }

        /**
         * Ensures that the given collection is not {@code null} and contains the specified element.
         *
         * @param element
         *         the element to find
         *
         * @throws AssertionError
         *         if the collection is {@code null} or if the specified element is not found
         */
        public void contains(final Object element) {
            contains(element, "Collection %s does not contain element '%s'", getValue(), element);
        }

        /**
         * Ensures that the given collection is not {@code null} and contains the specified element.
         *
         * @param element
         *         the element to find
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the collection is {@code null} or if the specified element is not found
         */
        @FormatMethod
        public void contains(final Object element, final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (!getValue().contains(element)) {
                throwException(explanation, args);
            }
        }

        /**
         * Ensures that the given collection is not {@code null} and does not contain the specified element.
         *
         * @param element
         *         the element that must not be in the collection
         *
         * @throws AssertionError
         *         if the collection is {@code null} or if the specified element is part of the collection
         */
        public void doesNotContain(final Object element) {
            doesNotContain(element, "Collection '%s' contains element '%s'", getValue(), element);
        }

        /**
         * Ensures that the given collection is not {@code null} and does not contain the specified element.
         *
         * @param element
         *         the element that must not be in the collection
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the collection is {@code null} or if the specified element is part of the collection
         */
        @FormatMethod
        public void doesNotContain(final Object element, final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (getValue().contains(element)) {
                throwException(explanation, args);
            }
        }
    }

    /**
     * Assertions for arrays.
     */
    public static class ArrayCondition extends ObjectCondition<Object[]> {
        /**
         * Creates a new instance of {@link IterableCondition}.
         *
         * @param values
         *         value of the condition
         */
        @SuppressWarnings({"PMD.UseVarargs", "AvoidObjectArrays"})
        public ArrayCondition(@CheckForNull final Object[] values) {
            super(values);
        }

        /**
         * Ensures that the given array is not {@code null} and contains at least one element. Additionally, ensures
         * that each element of the array is not {@code null}.
         *
         * @throws AssertionError
         *         if the array is empty (or {@code null}), or at least one array element is {@code null}.
         */
        public void isNotEmpty() {
            isNotEmpty("Array is empty or NULL");
        }

        /**
         * Ensures that the given array is not {@code null} and contains at least one element. Additionally, ensures
         * that each element of the array is not {@code null}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the array is empty (or {@code null}), or at least one array element is {@code null}.
         */
        @FormatMethod
        public void isNotEmpty(final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (getValue().length == 0) {
                throwException(explanation, args);
            }
            else {
                for (Object object : getValue()) {
                    if (object == null) {
                        throwException(explanation, args);
                    }
                }
            }
        }
    }

    /**
     * Assertions for strings.
     */
    public static class StringCondition extends ObjectCondition<String> {
        /**
         * Creates a new instance of {@code StringCondition}.
         *
         * @param value
         *         value of the condition
         */
        public StringCondition(@CheckForNull final String value) {
            super(value);
        }

        /**
         * Ensures that the given string is not {@code null} and contains at least one character.
         *
         * @throws AssertionError
         *         if the string is empty (or {@code null})
         */
        public void isNotEmpty() {
            isNotEmpty("The string is empty or NULL");
        }

        /**
         * Ensures that the given string is not {@code null} and contains at least one character.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the string is empty (or {@code null})
         */
        @FormatMethod
        public void isNotEmpty(final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (getValue().isEmpty()) {
                throwException(explanation, args);
            }
        }

        /**
         * Ensures that the given string is not {@code null} and contains at least one non-whitespace character.
         *
         * @throws AssertionError
         *         if the string is empty (or {@code null})
         */
        public void isNotBlank() {
            isNotBlank("The string is blank");
        }

        /**
         * Ensures that the given string is not {@code null} and contains at least one non-whitespace character.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the string is empty (or {@code null})
         */
        @FormatMethod
        public void isNotBlank(final String explanation, final Object... args) {
            isNotNull();

            if (isBlank()) {
                throwException(explanation, args);
            }
        }

        private boolean isBlank() {
            var string = getValue();
            if (string.isEmpty()) {
                return true;
            }
            for (int i = 0; i < string.length(); i++) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Assertions for objects.
     *
     * @param <T>
     *         type to check
     */
    public static class ObjectCondition<T> {
        @CheckForNull
        private final T value;
        @CheckForNull
        private final Object[] additionalValues;

        /**
         * Creates a new instance of {@code ObjectCondition}.
         *
         * @param value
         *         value of the condition
         */
        public ObjectCondition(@CheckForNull final T value) {
            this(value, new Object[0]);
        }

        /**
         * Creates a new instance of {@code ObjectCondition}.
         *
         * @param value
         *         value of the condition
         * @param additionalValues
         *         additional values of the condition
         */
        @SuppressWarnings("PMD.ArrayIsStoredDirectly")
        public ObjectCondition(@CheckForNull final T value, @CheckForNull final Object... additionalValues) {
            this.value = value;
            this.additionalValues = additionalValues;
        }

        /**
         * Ensures that the given object is not {@code null}.
         *
         * @throws AssertionError
         *         if the object is {@code null}
         */
        public void isNotNull() {
            isNotNull("Object is NULL");
        }

        /**
         * Ensures that the given object is not {@code null}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the object is {@code null}
         */
        @FormatMethod
        public void isNotNull(final String explanation, final Object... args) {
            if (value == null || additionalValues == null) {
                throwNullPointerException(explanation, args);
            }
            else {
                for (Object additionalValue : additionalValues) {
                    if (additionalValue == null) {
                        throwNullPointerException(explanation, args);
                    }
                }
            }
        }

        @SuppressFBWarnings("NP")
        @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
        T getValue() {
            if (value == null) {
                throw new NullPointerException("Value is null");
            }
            return value;
        }

        /**
         * Ensures that the given object is {@code null}.
         *
         * @throws AssertionError
         *         if the object is not {@code null}
         */
        public void isNull() {
            isNull("Object is not NULL");
        }

        /**
         * Ensures that the given object is {@code null}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the object is not {@code null}
         */
        @FormatMethod
        public void isNull(final String explanation, final Object... args) {
            if (value != null) {
                throwException(explanation, args);
            }
        }

        /**
         * Ensures that the given object is an instance of one of the specified types.
         *
         * @param type
         *         the type to check the specified object for
         * @param additionalTypes
         *         the additional types to check the specified object for
         *
         * @throws AssertionError
         *         the specified object is not an instance of the given type (or {@code null})
         */
        public void isInstanceOf(final Class<?> type, final Class<?>... additionalTypes) {
            isNotNull();

            List<Class<?>> types = new ArrayList<>();
            types.add(type);
            Collections.addAll(types, additionalTypes);

            for (Class<?> clazz : types) {
                if (clazz.isInstance(value)) {
                    return;
                }
            }
            throwException("Object is of wrong type. Actual: %s. Expected one of: %s", value, types);
        }

        /**
         * Ensures that the given object is an instance of the specified type.
         *
         * @param type
         *         the type to check the specified object for
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         the specified object is not an instance of the given type (or {@code null})
         */
        @FormatMethod
        public void isInstanceOf(final Class<?> type, final String explanation, final Object... args) {
            isNotNull(explanation, args);

            if (!type.isInstance(value)) {
                throwException(explanation, args);
            }
        }
    }

    /**
     * Assertions for booleans.
     */
    public static class BooleanCondition {
        /** The value of the condition. */
        private final boolean value;

        /**
         * Creates a new instance of {@code BooleanCondition}.
         *
         * @param value
         *         value of the condition
         */
        public BooleanCondition(final boolean value) {
            this.value = value;
        }

        /**
         * Ensures that the given condition is {@code false}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the condition is {@code true}
         */
        @FormatMethod
        public void isFalse(final String explanation, final Object... args) {
            if (value) {
                throwException(explanation, args);
            }
        }

        /**
         * Ensures that the given condition is {@code false}.
         *
         * @throws AssertionError
         *         if the condition is {@code true}
         */
        public void isFalse() {
            isFalse("Value is not FALSE");
        }

        /**
         * Ensures that the given condition is {@code true}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         if the condition is {@code false}
         */
        @FormatMethod
        public void isTrue(final String explanation, final Object... args) {
            if (!value) {
                throwException(explanation, args);
            }
        }

        /**
         * Ensures that the given condition is {@code true}.
         *
         * @throws AssertionError
         *         if the condition is {@code false}
         */
        public void isTrue() {
            isTrue("Value is not TRUE");
        }
    }

    /**
     * Assertions for exceptions.
     */
    public static class ExceptionCondition {
        @CheckForNull
        private final Throwable value;

        /**
         * Creates a new instance of {@link BooleanCondition}.
         *
         * @param value
         *         value of the condition
         */
        public ExceptionCondition(@CheckForNull final Throwable value) {
            this.value = value;
        }

        /**
         * Ensures that the exception is never thrown. I.e., this method will always throw an {@link AssertionError}.
         *
         * @param explanation
         *         a {@link Formatter formatted message} explaining the assertion
         * @param args
         *         Arguments referenced by the format specifiers in the formatted explanation. If there are more
         *         arguments than format specifiers, the extra arguments are ignored. The number of arguments is
         *         variable and may be zero.
         *
         * @throws AssertionError
         *         always thrown
         */
        @FormatMethod
        public void isNeverThrown(final String explanation, final Object... args) {
            throw new AssertionError(explanation.formatted(args), value);
        }
    }
}
