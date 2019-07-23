# Abstract Test Pattern

Mit dem **Abstract Test Pattern** lassen sich Schnittstellenverträge (d.h. Interfaces) und abstrakte Klassen 
testen. Damit kann sichergestellt werden, dass Subklassen (bzw. Klassen, die ein gegebenes Interface
implementieren) sich an den vereinbarten Vertrag halten. Das **Abstract Test Pattern** ist prinzipiell eine Komposition
aus zwei anderen Design Patterns: dem **Template Method Pattern** und dem **Factory Method Pattern**. 
Der konkrete Testfall wird als eine Template Method umgesetzt. Das darin benötigte Subject under Test wird durch eine 
abstrakte Factory Method erzeugt, die von der jeweiligen konkreten Subtestklasse überschrieben werden muss. An  
Beispielen lässt sich diese Herangehensweise am besten zeigen.

## Testen des Schnittstellenvertrags von equals

Im JDK ist für die Methode `Object.equals` ein recht umfangreiche Vertrag im JavaDoc formuliert. Hier der wichtigste
 Teil als Ausschnitt:

```java
/**
 * Indicates whether some other object is "equal to" this one.
 * <p>
 * The {@code equals} method implements an equivalence relation
 * on non-null object references:
 * <ul>
 * <li>It is <i>reflexive</i>: for any non-null reference value
 *     {@code x}, {@code x.equals(x)} should return
 *     {@code true}.
 * <li>For any non-null reference value {@code x},
 *     {@code x.equals(null)} should return {@code false}.
 * </ul>
 * [...]
 */
```

Diesen Vertrag müssen alle Klassen einhalten, die die `equals` Methode überschreiben. Um dies sicherzustellen,
lässt sich dieser JavaDoc Kommentar in einen äquivalenten Test umwandeln:

```java
/**
 * Verifies that objects of any Java class comply with the contract in {@link Object#equals(Object)}.
 */
public abstract class AbstractEqualsTest {
    /**
     * Creates the subject under test.
     *
     * @return the SUT
     */
    protected abstract Object createSut();

    /**
     * Verifies that equals is <i>reflexive</i>: for any non-null reference value {@code x}, {@code x.equals(x)} should
     * return {@code true}.
     */
    @Test
    public void shouldReturnTrueOnEqualsThis() {
        Object sut = createSut();

        assertThat(sut.equals(sut)).isTrue();
    }

    /**
     * Verifies that for any non-null reference value {@code x}, {@code x.equals(null)} should return {@code false}.
     */
    @Test
    public void shouldReturnFalseOnEqualsNull() {
        assertThat(createSut().equals(null)).isFalse();
    }
}
```

Jede Klasse, die `equals` überschreibt, kann den Schnittstellenvertrag mit folgenden 2 Schritten prüfen:

1. Erzeugen einer Subklasse von [AbstractEqualsTest](../src/test/java/edu/hm/hafner/util/AbstractEqualsTest.java).
2. Überschrieben der Factory Method `createSut`.

Die restlichen Tests der zu überprüfenden Klassen werden anschließend wie gewohnt in der Testklasse kodiert.

## Testen des Comparable Schnittstellenvertrags 

Das gleiche Verfahren lässt sich auch mit Interfaces umsetzen. 
Z.B. muss das Interface `Comparable` so implementiert werden, dass die Operation `compareTo` symmetrisch ist:

```java
/**
 * Compares this object with the specified object for order.  Returns a
 * negative integer, zero, or a positive integer as this object is less
 * than, equal to, or greater than the specified object.
 *
 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
 * <tt>y.compareTo(x)</tt> throws an exception.)
 * [...]
 */
public int compareTo(T o);
```

Ein dazu passender abstrakter Test könnte als 
[AbstractComparableTest](../src/test/java/edu/hm/hafner/util/AbstractComparableTest.java) 
folgendermaßen umgesetzt werden: 

```java
/**
 * Verifies that comparable objects comply with the contract in {@link Comparable#compareTo(Object)}.
 */
public abstract class AbstractComparableTest <T extends Comparable<T>> {
    /**
     * Verifies that a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
     * than the specified object.
     */
    @Test
    public void shouldBeNegativeIfThisIsSmaller() {
        T smaller = createSmallerSut();
        T greater = createGreaterSut();

        assertThat(smaller.compareTo(greater)).isNegative();
        assertThat(greater.compareTo(smaller)).isPositive();

        assertThat(smaller.compareTo(smaller)).isZero();
        assertThat(greater.compareTo(greater)).isZero();
    }

    /**
     * Verifies that {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))} for all {@code x} and {@code y}.
     */
    @Test
    public void shouldBeSymmetric() {
        T left = createSmallerSut();
        T right = createGreaterSut();

        int leftCompareToResult = left.compareTo(right);
        int rightCompareToResult = right.compareTo(left);

        assertThat(Integer.signum(leftCompareToResult)).isEqualTo(-Integer.signum(rightCompareToResult));
    }

    /**
     * Creates a subject under test. The SUT must be smaller than the SUT of the opposite method {@link
     * #createGreaterSut()}.
     *
     * @return the SUT
     */
    protected abstract T createSmallerSut();

    /**
     * Creates a subject under test. The SUT must be greater than the SUT of the opposite method {@link
     * #createSmallerSut()}.
     *
     * @return the SUT
     */
    protected abstract T createGreaterSut();
}
```

## Testen des Serializable Schnittstellenvertrags 

Als letztes Beispiel für das Abstract Test Pattern soll das `Serializable`  Interface dienen. Interessanterweise ist dies
ein Markerinterface, d.h. ein Interface ohne Definition eigener Methoden.  Der JavaDoc für das Interface ist allerdings 
sehr umfangreich und umfasst mehrere Seiten. Die Frage ist auch hier, wie man einen Test zur Verfügung stellen kann,
der den Vertrag einer Klasse überprüft, die `Serializable` ist. U.A. muss folgender Punkt erfüllt sein:
Eine Instanz der Klasse muss mit einem `ObjectOutputStream` in einen Bytestream umgewandelt werden können. Anschließend 
muss dieser Bytestream mit einem `ObjectInputStream` wieder zurück in eine Instanz der Klasse verwandelt werden können. 
Die beiden Instanzen müssen hinterher gleich sein (bezüglich `equals`).

```
/**
 * Base class to test the serialization of instances of {@link Serializable}. Note that the instances under test must
 * override equals so that the test case can check the serialized instances for equality.
 *
 * @param <T>
 *         concrete type of the {@link Serializable} under test
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
```

Dieser Test kann auch als Ausgangsbasis für weitere Tests im Bereich `Serializable` verwendet werden. Z.B. wird in
dieser Test meinem Projekt [Static Analysis Model and Parsers Library](https://github.com/jenkinsci/analysis-model/) 
benutzt, um sicherzustellen, dass sich die Serialisierung einer Klasse sich nicht aus Versehen verändert, so
dass die mit einer alten Version der Bibliothek serialisierten Daten plötzlich in der neuen Version nicht mehr 
einlesbar sind: 
[IssueTest#shouldReadIssueFromOldSerialization](https://github.com/jenkinsci/analysis-model/blob/master/src/test/java/edu/hm/hafner/analysis/IssueTest.java#L306). 
Dazu wird der gleiche Mechanismus benötigt, zusätzlich muss die Serialisierung einer alten Instanz
als Datei abgelegt werden.    
 
## Typische Anwendungsgebiete des Abstract Test Patterns

Neben solchen API Tests wird das Pattern hauptsächlich genutzt, um für den Code von abstrakten Klassen auch Testfälle 
zur Verfügung zu stellen. Diese Testfälle können dann von Subklassen einfach mitbenutzt werden. So kann sicher
gestellt werden, dass Subklassen den Vertrag einer Vererbungshierarchie nicht brechen und damit nicht unbewusst das 
[Liskov Substitution Principle](http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod) verletzen.
