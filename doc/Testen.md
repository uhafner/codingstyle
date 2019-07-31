# Testen

Wenn möglich, nutzen wir das Prinzip des Test Driven Development. D.h. Tests werden vorab bzw. zeitgleich mit
den zu erstellenden Klassen geschrieben. Details zu diesem Ansatz finden sich in [4] in Kapitel 9. Dies hat viele
Vorteile:
- Es werden nur die Anforderungen umgesetzt, die auch wirklich nötig sind.
    - **YAGNI**: You ain‘t gonna need it!
    - **KISS**: Keep it simple, stupid!
- Das Softwaredesign orientiert sich an der Nutzung von Klassen, da die Tests die ersten "Anwender" des API sind.
- Testfälle dokumentieren eine Klasse (und ergänzen somit die Spezifikation).
- Testbarkeit eines Programms ist per Definition garantiert und zudem erhalten wir automatisch eine hohe Testabdeckung.

## Konventionen beim Schreiben von Modultests

Wir nutzen für Modultests (d.h. Unittests) die [JUnit](https://junit.org/) Bibliothek in der Version 5. Alle Modultests 
einer Klasse `Foo` legen wir in der zugehörigen Klasse `FooTest` ab. (Alternativ könnte auch der Suffix `Tests` verwendet
werden, da ja eine Testklasse typischerweise mehrere Tests enthält - das ist aber Geschmackssache.) 
Testklassen verwenden dasselbe Package wie die zu testende Klasse. Die Tests werden im Verzeichnis `src/test/java` 
abgelegt, damit sie separat von den eigentlichen Klassen liegen (diese liegen unter `src/main/java`). 

Gemäß der in JUnit 5 eingeführten Konventionen haben Test Klassen und Methoden die Sichtbarkeit *package private*. 
Damit Testfälle als solches erkannt werden, müssen sie mit der Annotation `@org.junit.jupiter.api.Test` markiert werden.

Ein Modultest besteht immer aus drei Schritten, die ggf. zusammenfallen können:

1. **Given**: Das zu testende Objekt wird erzeugt (auch Subject Under Test genannt und SUT abgekürzt). Sind dazu weitere Objekte nötig, 
so werden diese in diesem Schritt ebenso erzeugt. Sollte das Erzeugen dieser zusätzlich erforderlichen Objekte mehr
als ein paar Zeilen Code erfordern, so sollten diese Objekte in einzelnen `create` Methoden erzeugt werden. Damit
ist eine Wiederverwendung in anderen Testfällen leichter möglich.
2. **When**: Die zu überprüfende Funktionalität wird aufgerufen. Sind dazu weitere Objekte nötig (z.B. als Methodenparameter),
sollten diese bereits in Schritt 1.) erzeugt werden.
3. **Then**: Es wird überprüft, ob die im letzten Schritt aufgerufene Funktionalität korrekt ist. Dazu kann z.B. der
Rückgabewert einer Methode oder der innere Zustand einer Klasse herangezogen werden. Zum Prüfen verwenden wir Assertions
des JUnit Frameworks [AssertJ](https://assertj.github.io/doc/) bzw. 
die `verify` Methoden eines mocks. 

Die Benennung der drei Schritte in **Given-When-Then** stammt aus dem 
[Behavior-Driven-Development](https://dannorth.net/introducing-bdd/) und ist in einem 
[Artikel von Martin Fowler](https://martinfowler.com/bliki/GivenWhenThen.html) gut beschrieben. 
Es gibt auch noch die Begriffe **Arrange-Act-Assert** und **Setup-Excercise-Verify**, die synonym dazu verwendet 
werden können.

Damit im Fehlerfall schnell die Ursache gefunden wird, benennen wir eine Testmethode mit einem sinnvollen 
(und ausreichend langem) Namen
und ergänzen im JavaDoc in einem knappen Satz das Ziel des Tests. Eine sinnvolle Namenskonvention für Tests ist der 
Präfix *should* mit einer angehängten Beschreibung, die die Eigenschaften des SUT beschreiben, die im Test überprüft werden 
(bzw. das Ziel des Tests). Dies ist aber keine Pflicht, wichtig ist eine gute Benennung.

Ein Test kann durchaus mehrere Szenarien enthalten, die aufeinander aufbauen. Ebenso ist die Verwendung von mehreren 
Assertions im **When** Teil erlaubt. 

An einem Beispiel lassen sich diese Konventionen am besten erkennen:
```java
package edu.hm.hafner.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link TreeStringBuilder}.
 */
class TreeStringBuilderTest {
    /** 
     * Creates several tree strings and checks that new tree strings will use 
     * the prefix of a previous one. 
     */
    @Test
    void shouldCreateSimpleTreeStringsWithBuilder() {
        // Given
        TreeStringBuilder builder = new TreeStringBuilder();
        
        // When
        TreeString foo = builder.intern("foo");
        
        // Then
        assertThat(foo).hasToString("foo");
        assertThat(foo.getLabel()).isEqualTo("foo");

        // When
        TreeString treeString = builder.intern("foo/bar/zot");

        // Then
        assertThat(treeString).hasToString("foo/bar/zot");
        assertThat(treeString.getLabel()).isEqualTo("/bar/zot");
        assertThat(treeString.getParent()).isSameAs(foo);
    }
}
```

## Testen von Exceptions

Kann ein Konstruktor oder eine Methode eine Exception werfen, so muss dies auch getestet werden. Dazu wird das gleiche
Vorgehen verwendet, d.h. der Test wird in **Given-When-Then** aufgeteilt. Am elegantesten wird ein Exception
Test mit [Lambda-Ausdrücken](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html) 
und der Assertion `assertThatThrownBy` aus AssertJ:
 
```java
    @Test
    void shouldThrowAssertionErrorIfLabelIsEmpty() {
        assertThatThrownBy(() -> new TreeString(new TreeString(), ""))
                .isInstanceOf(AssertionError.class);
    }
``` 

Alternativ kann auch die Syntax `assertThatExceptionOfType` genutzt werden:

```java
    @Test
    void shouldThrowAssertionErrorIfLabelIsEmpty() {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> new TreeString(new TreeString(), ""));
    }
``` 

Wichtig ist, dass der Lambda Block nur genau die Anweisung enthält, die die Exception wirft. Dies hat den Vorteil -
auch gegenüber dem JUnit Pedant `@Test(expected = Exception.class)` - dass die Exception nur an der genau bestimmten
 Stelle überprüft wird. Wird eine Exception zufällig an einer anderen Stelle geworfen, so wird das als Testfehler
  markiert.

## Bedingte Ausführung von Tests

In Ausnahmefällen macht es Sinn, dass ein Testfall nur unter bestimmten Voraussetzungen gestartet wird. Ein typischer
Anwendungsfall ist die Behandlung unterschiedlicher Betriebssysteme. In so einem Fall können Tests mit einem sogenannten
*Guard* versehen werden, der den Test überspringt, falls die Voraussetzungen nicht erfüllt sind: wir verwenden  in
so einem Fall die Methode `assumeThat` der AssertJ Bibliothek.  

Beispielweise gibt es für die Klasse `PathUtils` jeweils einen Testfall für Windows und Unix Systeme:

```java
@Test
void shouldSkipAlreadyAbsoluteOnUnix() {
    assumeThat(isWindows()).isFalse();

    PathUtil pathUtil = new PathUtil();

    assertThat(pathUtil.createAbsolutePath("/tmp/", "/tmp/file.txt")).isEqualTo("/tmp/file.txt");
}

@Test
void shouldSkipAlreadyAbsoluteOnWindows() {
    assumeThat(isWindows()).isTrue();

    PathUtil pathUtil = new PathUtil();

    assertThat(pathUtil.createAbsolutePath("C:\\tmp", "C:\\tmp\\file.txt")).isEqualTo("C:/tmp/file.txt");
}
```
  
## Allgemeine Testszenarien

In JUnit gibt es die Möglichkeit, sich ein oder mehrere Testszenarien über speziell dafür markierte Methoden aufzubauen.
Dazu müssen diese Methoden mit `@BeforeEach`, `@BeforeAll`, `@AfterEach`, `@AfterAll`, etc. annotiert werden, und die 
erzeugten SUT (und abhängigen Objekte) in Objektvariablen (Fields) gespeichert werden. Dieses Vorgehen ist bequem, 
macht Testfälle jedoch unübersichtlich und schwer verständlich, da die im Test verwendeten Objekte nicht direkt sichtbar sind. 
Daher verwenden wir diese Annotationen nicht. Generell gilt: Test Klassen sollen keine Objektvariablen besitzen. Statt 
dessen sollten benötigte Objekte immer neu mit passenden `create` Methoden erzeugt werden: so können die erzeugten 
Objekte in den einzelnen Tests unabhängig voneinander geändert werden können.

## Aussagekräftige Fehlermeldungen

Ein wichtiger Schritt im TDD ist die Validierung, ob der **Test** überhaupt korrekt ist. D.h. wir müssen als erstes 
sicherstellen, dass ein Test zunächst einmal fehlschlägt, wenn die zu testende Methode noch unvollständig ist. 
An dieser Stelle lohnt es sich, die Fehlermeldung zu analysieren: ist diese nicht aussagekräftig, 
sollte diese mit der Methode `as` entsprechend verbessert werden: 

```java
assertThat(list.size()).as("Wrong number of list elements").isEqualTo(5);
```   

## Eigenschaften von Modultests

Gut geschriebene Modultests lassen sich nach dem [FIRST](https://pragprog.com/magazines/2012-01/unit-tests-are-first)
Prinzip charakterisieren, das sich folgendermaßen zusammenfassen lässt:
- Sie sind unabhängig und können in beliebiger Reihenfolgen laufen.
- Sie sind konsistent und liefern immer das gleiche Resultat.
- Sie können schnell und automatisiert ausgeführt werden.
- Sie sind verständlich und wartungsfreundlich.

## Tipps und Tricks

Hier noch einige Anregungen bei der Gestaltung von Modultests:
- Eine Testmethode sollte nur einen Aspekt testen: d.h. wir testen ein bestimmtes Verhalten und nur indirekt eine Methode.
- Testmethoden sollten ca. 5-15 Zeilen umfassen.
- Modultests greifen i.A. nie auf Datenbank, Dateisystem oder Web Services zu.
- Häufig verwendete Eingangsparameter sollten als Konstanten definiert werden. 
- Tests nutzen die selben Kodierungsrichtlinien wie normale Klassen.

## Testfall Anzahl

Die Anzahl der erforderlichen Tests für eine Klasse lässt sich schwer herleiten. Daher sollten folgende Kriterien
herangezogen werden:
- Jede nicht-triviale public Methode einer Klasse mit mindestens einem Testfall überprüfen
  - Randwerte (0, -1, 1, etc.) verwenden
  - Eingabeparameter mit unerwarteten Werten (null, {}, etc.) belegen
- Äquivalenzklassen bilden: minimale Anzahl Tests für maximale Variation der Testdaten
- Zu jedem entdeckten Fehler (z.B. durch einen Bug Report) einen passenden Testfall erstellen

Sinnvollerweise nutzt man die [Coverage Übersicht](https://www.jetbrains.com/idea/help/code-coverage.html) 
der Entwicklungsumgebung, um zu überprüfen, welcher Teil des Quelltextes bereits getestet wurde.

## Weiterführende Themen

Die folgenden Abschnitte referenzieren einige weiterführende Themen.
 
### Testen von Basisklassen

Abstrakte Klassen und Schnittstellen lassen sich ebenso testen: dazu wird das 
Abstract Test Pattern benutzt, das in einem [eigenen Abschnitt](Abstract-Test-Pattern.md) beschrieben ist.

### State Based vs. Interaction Based Testing

Prinzipiell gibt es zwei Varianten des Testings. Beim **State Based Testing** wird das Testobjekt nach Aufruf der zu 
testenden Methoden durch Abfrage seines internen Zustands verifiziert. Im Gegensatz dazu wird beim 
**Interaction Based Testing** nicht der Zustand des SUT analysiert. Statt dessen werden die Aufrufe aller am Test 
beteiligten Objekte überprüft. Details dazu sind im eigenen Abschnitt
[State Based vs. Interaction Based Testing](State-Based-Vs-Interaction-Based.md) beschrieben.
