package edu.hm.hafner.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static org.assertj.core.api.Assertions.*;

/**
 * Base class to test the serialization of instances of {@link Serializable}. Note that the instances under test must
 * override equals so that the test case can check the serialized instances for equality.
 *
 * @param <T>
 *         concrete type of the {@link Serializable} under test
 *
 * @author Ullrich Hafner
 */
public abstract class SerializableTest<T extends Serializable> extends ResourceTest {
    /**
     * Factory method to create an instance of the {@link Serializable} under test. The instance returned by this method
     * will be serialized to a byte stream, deserialized into an object again, and finally compared to another instance
     * using {@link Object#equals(Object)}.
     *
     * @return the subject under test
     */
    protected abstract T createSerializable();

    @Test
    @DisplayName("should be serializable: instance -> byte array -> instance")
    void shouldBeSerializable() {
        T serializableInstance = createSerializable();

        byte[] bytes = toByteArray(serializableInstance);

        assertThatSerializableCanBeRestoredFrom(bytes);
    }

    /**
     * Resolves the subject under test from an array of bytes and compares the created instance with the original
     * subject under test.
     *
     * @param serializedInstance
     *         the byte stream of the serializable
     */
    protected void assertThatSerializableCanBeRestoredFrom(final byte... serializedInstance) {
        assertThatRestoredInstanceEqualsOriginalInstance(createSerializable(), restore(serializedInstance));
    }

    /**
     * Asserts that the instance restored from the serialization is equal to the original instance before the
     * serialization. By default, the {@link ObjectAssert#usingRecursiveComparison() recursive comparison strategy} of
     * AssertJ is used to compare these instances. If your subject under test overrides
     * {@link Object#equals(Object) equals}, then you should override this method with {@code original.equals(restored)}
     * so the customized equals method will be used.
     *
     * @param original
     *         the instance before the serialization
     * @param restored
     *         the instance restored by the de-serialization
     */
    protected void assertThatRestoredInstanceEqualsOriginalInstance(final T original, final T restored) {
        assertThat(restored).usingRecursiveComparison().isEqualTo(original);
    }

    /**
     * Deserializes the subject under test from an array of bytes.
     *
     * @param serializedInstance
     *         the byte stream of the serializable
     *
     * @return the deserialized instance
     */
    @SuppressWarnings({"unchecked", "BanSerializableRead"})
    protected T restore(final byte[] serializedInstance) {
        Object object;

        try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(serializedInstance))) {
            object = inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new AssertionError("Can't resolve instance from byte array", e);
        }

        return (T) object;
    }

    /**
     * Write the specified object to a byte array using an {@link ObjectOutputStream}. The class of the object, the
     * signature of the class, and the values of the non-transient and non-static fields of the class and all of its
     * supertypes are written. Objects referenced by this object are written transitively so that a complete equivalent
     * graph of objects can be reconstructed by an ObjectInputStream.
     *
     * @param object
     *         the object to serialize
     *
     * @return the object serialization
     */
    protected byte[] toByteArray(final Serializable object) {
        var out = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(out)) {
            stream.writeObject(object);
        }
        catch (IOException exception) {
            throw new IllegalStateException("Can't serialize object " + object, exception);
        }
        return out.toByteArray();
    }

    /**
     * Serializes an issue using an {@link ObjectOutputStream } to the file /tmp/serializable.ser.
     *
     * @throws IOException
     *         if the file could not be created
     */
    @SuppressFBWarnings("DMI")
    protected void createSerializationFile() throws IOException {
        Files.write(Paths.get("/tmp/serializable.ser"), toByteArray(createSerializable()),
                StandardOpenOption.CREATE_NEW);
    }
}
