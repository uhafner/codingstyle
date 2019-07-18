# State Based vs. Interaction Based Testing

Prinzipiell gibt es zwei Varianten des Testings: das **State Based Testing** und das **Interaction Based Testing**.

## State Based Testing

Beim **State Based Testing** wird das Testobjekt nach Aufruf der zu 
testenden Methoden durch Abfrage seines internen Zustands verifiziert. Analog dazu kann natürlich auch der Zustand 
der im Test verwendeten Parameter bzw. Rückgabewerte analysiert werden. Die meisten Tests eines Projekts 
laufen nach diesem Muster ab und können folgendermaßen formuliert werden:

```java
/** [Kurze Beschreibung: was genau macht der Test] */
@Test
void should[restlicher Methodenname der den Test fachlich beschreibt]() {
    // Given
    [Test Setup: Erzeugung der Parameter, die das SUT zum Erzeugen bzw. beim Aufruf benötigt]
    [Erzeugung des SUT]
    // When
    [Aufruf der zu testenden Methoden]
    // Then
    [Verifikation des Zustands des SUT bzw. von Parametern oder Rückgabewerten mittels AssertJ]
}
```


## Interaction Based Testing

Im Gegensatz dazu wird beim **Interaction Based Testing** nicht der Zustand des SUT analysiert. Statt dessen werden die 
Aufrufe aller am Test beteiligten Objekte mit einem Mocking Framework wie [Mockito](https://site.mockito.org) überprüft.
D.h. hier steht nicht der Zustand des Testobjekts im Vordergrund, sondern die Interaktion mit beteiligten Objekten. Ein
typischer Testfall nach dem Interaction Based Testing ist folgendermaßen aufgebaut:

```java
/** [Kurze Beschreibung: was genau macht der Test] */
@Test
void should[restlicher Methodenname der den Test fachliche beschreibt]() {
    // Given
    [Test Setup 1: Erzeugung der Mocks, die zur Verifikation benötigt werden]
    [Test Setup 2: Erzeugung der Stubs, die das SUT zum Erzeugen bzw. beim Aufruf benötigt]
    [Erzeugung des SUT]
    // When
    [Aufruf der zu testenden Methoden]
    // Then
    [Verifikation des Zustands der Mocks]
}
```

Ein typisches Beispiel für solch einen Test ist in der folgenden Klasse zu finden:

 ```java
package edu.hm.hafner.util;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link PrefixLogger}.
 *
 * @author Ullrich Hafner
 */
class PrefixLoggerTest {
    private static final String LOG_MESSAGE = "Hello PrefixLogger!";
    private static final String PREFIX = "test";
    private static final String EXPECTED_PREFIX = "[test]";

    @Test
    void shouldLogSingleAndMultipleLines() {
        PrintStream printStream = mock(PrintStream.class);
        PrefixLogger logger = new PrefixLogger(printStream, PREFIX);

        logger.log(LOG_MESSAGE);

        verify(printStream).println(EXPECTED_PREFIX + " " + LOG_MESSAGE);
    }
}
```
