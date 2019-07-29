# Formatierung

Gute und sinnvolle Formatierung des Quelltextes ist eine wichtige Aufgabe, denn
Quelltext wird einmal geschrieben und zigmal gelesen. Das Layout sollte immer
der logischen Struktur des Codes folgen: Layout ist damit auch eine Kommunikationsform.

In den folgenden Abschnitten sind die wichtigsten Richtlinien zur Formatierung
von Java Code beschrieben. Eine detaillierte Beschreibung unterbleibt explizit,
da alle modernen Entwicklungsumgebungen vordefinierte Formatierungseinstellungen verwenden
können. Diese können bei Bedarf mit einem einfachen Kommando angewendet werden. D.h. um
die korrekte Formatierung eines Quelltextstücks zu sehen, ist lediglich das entsprechende
Kommando der Entwicklungsumgebung aufzurufen.

**Achtung:** Die Entwicklungsumgebung [Greenfoot](http://www.greenfoot.org) selbst ist nicht 
so mächtig und korrigiert nur die Einrückung, nicht aber die Verwendung von Leerzeichen.

## Einrücken

Die öffnende Klammer eines Blocks steht immer auf der gleichen Zeile wie die Anweisung davor. Die folgenden Anweisungen
eines geschachtelten Blocks werden alle mit 4 Leerzeichen eingerückt. Tabs dürfen nicht verwendet werden, da
diese nicht überall mit der gleichen Leerzeichenanzahl dargestellt werden (z.B. im Browser). 
Die schließende Klammer steht dann genau unterhalb der Anweisung, die die öffnende Klammer enthält.
 
An Beispielen wird das leichter deutlich, das zum Einrücken verwendete Leerzeichen wird zur besseren Lesbarkeit
durch das Sonderzeichen `⋅` hervorgehoben:

```java
if (expression1) {
⋅⋅⋅⋅statement1;
⋅⋅⋅⋅statement2;
⋅⋅⋅⋅etc.
}

while (expression2) {
⋅⋅⋅⋅statement3;
⋅⋅⋅⋅statement4;
⋅⋅⋅⋅etc.
⋅⋅⋅⋅if (expression3) {
⋅⋅⋅⋅⋅⋅⋅⋅statement5;
⋅⋅⋅⋅⋅⋅⋅⋅statement6;
⋅⋅⋅⋅⋅⋅⋅⋅etc.
⋅⋅⋅⋅}
}
```

**Achtung:** Zur besseren Unterstützung der visuellen Struktur steht gemäß [3] 
bei einem `if-else` und `try-catch` Konstrukt die schließende Klammer immer alleine auf einer Zeile. 
Viele Java Entwicklungsteams (z.B. das Oracle JDK Team) halten dies anders.

```java
if (expression1) {
⋅⋅⋅⋅statement1;
⋅⋅⋅⋅etc.
}
else if (expression2) {
⋅⋅⋅⋅statement2;
⋅⋅⋅⋅etc.
}
else {
⋅⋅⋅⋅statement3;
⋅⋅⋅⋅etc.
}

try {
⋅⋅⋅⋅statement4;
⋅⋅⋅⋅etc.
}
catch (Exception exception1) {
⋅⋅⋅⋅statement5;
⋅⋅⋅⋅etc.
}
finally {
⋅⋅⋅⋅statement6;
⋅⋅⋅⋅etc.
}
```

## Leerzeichen

Quelltext ohne Leerzeichen lässt sich deutlich schlechter lesen und verstehen. Daher nutzen wir **genau** 
ein Leerzeichen an den folgenden Stellen:
- Zwischen einer Anweisung und der folgenden öffnenden runden ( oder geschweiften { Klammer 
- Zwischen einer schließenden runden ) und einer öffnenden geschweiften { Klammer 
- Zwischen binärem Operator und seinen beiden Operanden
- Nach jedem Komma in der Parameterliste einer Methode

Für die folgenden Konstrukte wird kein Leerzeichen verwendet:
- Zwischen unärem Operator und seinem Operand
- Zwischen Methodenname und öffnender runden ( Klammer 
- Bedingung innerhalb der runden Klammern () im `if` oder `while`   

Hier ein echtes Beispiel, in denen das Leerzeichen durch das Sonderzeichen `⋅` hervorgehoben wurde:

```java
if (isAbsolute(fileName)⋅||⋅directory⋅==⋅null⋅||⋅directory.length()⋅==⋅0)⋅{
⋅⋅⋅⋅return makeUnixPath(fileName);
}
String⋅path⋅=⋅makeUnixPath(directory);

String⋅separator;
if⋅(path.endsWith(SLASH))⋅{
⋅⋅⋅⋅separator⋅=⋅StringUtils.EMPTY;
}
else⋅{
⋅⋅⋅⋅separator⋅=⋅SLASH;
}
```

## Zeilenumbruch

Heutzutage ist es selten nötig, eine Quelltextzeile umzubrechen, da meistens die Monitore breit genug sind. Wird
eine Zeile doch einmal zu lang, dann muss sie umgebrochen werden (typischerweise nach 120 Zeichen Breite): so wird
horizontales Scrolling im Editor vermieden.

Bei einem Umbruch ist darauf zu achten, dass i.A. vor einem Operator umgebrochen wird. Die umgebrochene Zeile wird dann
mit 8 Zeichen zusätzlich zur vorhergehenden Zeile eingerückt.

```java
Ensure.that(parent == null || !label.isEmpty())
⋅⋅⋅⋅⋅⋅⋅⋅.isTrue("if there's a parent '%s', label '%s' can't be empty", parent, label);
```

Das gleiche Schema wird verwendet beim Umbruch von Methodenparametern:

```java
public static boolean containsAnyIgnoreCase(@Nullable final CharSequence input, 
⋅⋅⋅⋅⋅⋅⋅⋅@Nullable final String... searchTexts) {
⋅⋅⋅⋅if (StringUtils.isEmpty(input)) {
⋅⋅⋅⋅⋅⋅⋅⋅return false;
⋅⋅⋅⋅}
    [...]
}

```

## Kommentare Formatieren

Für Kommentare gibt es auch einige Richtlinien, die im Abschnitt [Kommentare](Kommentare.md) aufgeführt sind.
Bei der Formatierung ist zusätzlich auf die folgende Punkte zu achten:
- Zu lange Zeilen werden wie normaler Code nach 120 Zeichen umgebrochen (siehe [oben](#Zeilenumbruch))
- Abschnitte nutzen korrekte Kennzeichnung mittels der XHTML Tags \<p\>Text\</p\> 
- Parameter werden auf einer neuen Zeile beschrieben (mit korrektem Einrücken)

Ein Beispiel sagt auch hier mehr als tausend Worte:

```java
/**
 * Checks if the provided string contains irrespective of case any of the strings 
 * in the given array, handling {@code null} strings. Case-insensitivity is 
 * defined as by {@link String#equalsIgnoreCase(String)}.
 *
 * <p>
 * A {@code null} {@code cs} CharSequence will return {@code false}. 
 * A {@code null} or zero length search array will return {@code false}.
 * </p>
 *
 * <pre>
 * StringUtils.containsAny(null, *)            = false
 * StringUtils.containsAny("", *)              = false
 * StringUtils.containsAny(*, null)            = false
 * StringUtils.containsAny(*, [])              = false
 * StringUtils.containsAny("abcd", "ab", null) = true
 * StringUtils.containsAny("abcd", "ab", "cd") = true
 * StringUtils.containsAny("abc", "d", "abc")  = true
 * StringUtils.containsAny("ABC", "d", "abc")  = true
 * </pre>
 *
 * @param input
 *         The string to check, may be {@code null}
 * @param searchTexts
 *         The strings to search for, may be empty. Individual CharSequences 
 *         may be null as well.
 *
 * @return {@code true} if any of the search CharSequences are found, 
 *         {@code false} otherwise
 */
public static boolean containsAnyIgnoreCase(@Nullable final CharSequence input, 
        @Nullable final String... searchTexts) {
    [...]
}

```
 
## Leerzeilen

Auch Leerzeilen können die Struktur von Programmen verbessern. Zusammenhängende Anweisungen sollten gruppiert werden
und durch Leerzeilen von unzusammenhängenden Anweisungen getrennt werden. Dadurch lässt sich eine Methode in
mehrere zusammenhängende Blöcke gruppieren. Die einzelnen Blöcke einer Methode sind dann durch **genau** eine Leerzeile
voneinander getrennt.

```java
public void foo() {
    block1-anweisung1;
    block1-anweisung2;
    block1-anweisung3;

    block2-anweisung1;
    block2-anweisung2;

    block3-anweisung1;
    block3-anweisung2;
    block3-anweisung3;
}
```

Die erste Anweisung beginnt dabei direkt nach dem Methodenkopf, die letzte hört direkt vor der schließenden Klammer auf,
hier werden keine extra Leerzeilen mehr eingefügt.

Innerhalb einer Klasse hat es sich eingebürgert, zwei Methoden oder Konstruktoren durch eine Leerzeile zu trennen. 
Nach dem Klassenkopf und vor der schließenden Klammer einer Klasse steht keine extra Leerzeile, ebenso nicht nach dem
Methodenkopf und der schließenden Klammer einer Methode.
Instanzvariablen können wie Anweisungen gruppiert werden, wenn dies thematisch sinnvoll ist. Zwischen Instanzvariablen
und Methoden bzw. Konstruktoren befindet sich wieder eine Leerzeile. I.a. werden alle Instanzvariablen direkt nach dem
Klassenkopf aufgeführt, dann alle Konstruktoren, dann alle Methoden. Am Schluss stehen dann alle inneren Klassen.

## Komplettes Beispiel

Am besten lassen sich die Regeln an einem realem Beispiel erkennen, das die typische Java Formatierung aufzeigt:

```java
package edu.hm.hafner.util;

import org.apache.commons.lang3.StringUtils;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * A simple helper class in the style of {@link StringUtils} that provides methods to check if strings contain
 * search strings.
 *
 * @author Ullrich Hafner
 */
public final class StringContainsUtils {
    /**
     * Checks if the provided string contains irrespective of case any of the strings in the given array,
     * handling {@code null} strings. Case-insensitivity is defined as by {@link String#equalsIgnoreCase(String)}.
     *
     * <p>
     * A {@code null} {@code cs} CharSequence will return {@code false}. A {@code null} or zero length search array will
     * return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)            = false
     * StringUtils.containsAny("", *)              = false
     * StringUtils.containsAny(*, null)            = false
     * StringUtils.containsAny(*, [])              = false
     * StringUtils.containsAny("abcd", "ab", null) = true
     * StringUtils.containsAny("abcd", "ab", "cd") = true
     * StringUtils.containsAny("abc", "d", "abc")  = true
     * StringUtils.containsAny("ABC", "d", "abc")  = true
     * </pre>
     *
     * @param input
     *         The string to check, may be {@code null}
     * @param searchTexts
     *         The strings to search for, may be empty. Individual CharSequences may be null as well.
     *
     * @return {@code true} if any of the search CharSequences are found, {@code false} otherwise
     */
    public static boolean containsAnyIgnoreCase(@Nullable final CharSequence input,
            @Nullable final String... searchTexts) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        if (searchTexts == null || searchTexts.length == 0) {
            return false;
        }

        for (String searchText : searchTexts) {
            if (StringUtils.containsIgnoreCase(input, searchText)) {
                return true;
            }
        }
        return false;
    }

    private StringContainsUtils() {
        // prevents instantiation
    }
}
```
