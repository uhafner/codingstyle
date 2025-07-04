String-Formatierung, Konvertierungen
- Erstellen von Strings mit `String.format(String format, Object... argumente)`
  - `%n` ist Zeilenumbruch
  - `%s` Platzhalter für String
  - `%d` Platzhalter für Ganzzahl, `%03d` mit drei Stellen und führender Null
  - `%f` Platzhalter für Kommazahl, `%.3f` mit 3 Nachkommastellen
- String zu Ganzzahl: `int number = Integer.parseInt(String number)`
- Ganzzahl zu String: `String text = String.valueOf(int number)`
- String zu Kommazahl: `double number = Double.parseDouble(String number)`
- Kommazahl zu String: `String text = String.valueOf(double number)`
- Direkte Ausgabe mit `System.out.printf(String format, Object... argumente)`

Methoden der Klasse Math
- `Math.max(a, b)`, `Math.min(a, b)`, `Math.abs(a)` - Maximum, Minimum, Absolutwert
- `Math.round(a)` – rundet `double` nach `long`, bzw. `float` nach `int` (kaufmännisch)
- `Math.pow(a, b)` – berechnet a hoch b
- `Math.sqrt(a)` – berechnet die Wurzel

Listen vs. Arrays

| Operation  | Array                                 | List (ArrayList oder LinkedList) |
|------------|---------------------------------------|----------------------------------|
| Definition | `Type[] array`                        | `List<Type> list`                |
| Erstellen  | `array = new Type[length]`            | `list = new ArrayList<Type>()`   |
| Lesen      | `Type element = array[index]`         | `Type element = list.get(index)` |
| Größe      | `array.length`                        | `list.size()`                    |
| Schreiben  | `array[index]` = element              | `list.set(index, element)`       |
| Anfügen    | nicht möglich                         | `list.add(element)`              |
|            |                                       | `list.addAll(elements)`          |
| Einfügen   | nicht möglich                         | `list.add(index, element)`       |
| Entfernen  | nicht möglich                         | `list.remove(element)`           |
|            |                                       | `list.removeAll(elements)`       |
|            |                                       | `list.remove(index)`             |
| Leeren     | nicht möglich                         | `list.clear()`                   |
| Suchen     | `ArrayUtils.indexOf(array, element)`  | `list.indexOf(element)`          |
|            | `ArrayUtils.contains(array, element)` | `list.contains(element)`         |
| Ausgabe    | `Arrays.toString(array)`              | `list.toString() `               |
| Vergleich  | `Arrays.equals(array, other)`         | `list.equals(other) `            |

Methoden der Klasse Set

- Erzeugen: `List<Type> set = new HashSet<Type>()`
- Hinzufügen: `set.add(element)`
- Entfernen: `Type element = set.remove(element)`
- Suchen: `set.contains(element)`
- Leeren: `set.clear()`

Methoden der Klasse Collections

- Sortieren: `Collections.sort(...)`
- Reihenfolge umdrehen: `Collections.reverse(...)`
- Mit Wert füllen: `Collections.fill(...)`

Methoden der Klasse String

- Suchen:
  - `boolean contains(String pattern)`
  - `int indexOf(String pattern)`
  - `int indexOf(String pattern, int from)`
- Größe: `length()`, `isEmpty()`, `isBlank()`
- Ersetzen: `String replace(String muster, String ersatz)`
- Teilbereiche: 
  - `String substring(int from)` 
  - `String substring(int from, int to)`
- `String strip()`, `String toUppercase()`, `String toLowercase()`
- String in Zeichen umwandeln: `char[] toCharArray()`
- Vergleichen (lexikografisch): `int compareTo(String other)`

Methoden der Klasse Character
- `boolean Character.isLetter(char c)` 
- `boolean Character.isDigit(char c)`
- `boolean Character.isWhitespace(char c)`
- `boolean Character.isUpperCase(char c)` 
- `boolean Character.isLowerCase(char c)`
- `for (char zeichen : text.toCharArray()) { ... }`

Testen
- Markierung einer Testmethode: `@Test`
- Assertions
  - `assertThat(Type expected).isEqualTo(Type actual)` (equals überschrieben)
  - `assertThat(Type expected).usingRecursiveComparison().isEqualTo(Type actual)` (sonst)
  - `assertThat(boolean expected).isTrue()` oder `assertThat(boolean expected).isFalse()`
  - `assertThat(String expected).contains("o").startsWith("Hello").endsWith("World")`
  - `assertThat(Collection<Type> expected).contains(Type actual1, Type actual2, ...)`
  - `assertThat(Collection<Type> expected).containsExactly(Type actual1, Type actual2, ...)`
  - `assertThat(Collection<Type> expected).first().isEqualTo(Type actual)`
  - `assertThatExceptionOfType(Type.class).isThrownBy(() -> [CODE]).withMessageContaining("Error");`

Exceptions
- Werfen mit `throw new Type("Meldungstext mit Kontext")`
- Fehlerbehandlung mit
```
try {
   ... regulärer Code ...
}
catch (ExceptionTypA1 | ... | ExceptionTypAN exception) {
   ... Fehlerbehandlung A ...
}
catch (ExceptionTypB1 | ... | ExceptionTypBN exception) {
   ... Fehlerbehandlung B ...
}
```

Sichtbarkeiten
- public: `public int method() {}` (Klassen, Konstruktoren, Methoden)
- protected: `protected int method() {}` (Konstruktoren, Methoden)
- package private: `int method() {}` (Klassen, Konstruktoren, Methoden)
- private: `private int method() {}` (Klassen, Methoden, Objektvariablen)

Vererbung und Interfaces
- Implementieren eines Interfaces: `class Type implements Interface1, Interface2 {}`
- Erweitern einer Klasse: `class Type extends SuperType {}`
- Markieren einer überschriebenen Methode: `@Override`
- Markieren einer implementierten Methode: `@Override`
- Auszeichnen einer abstrakten Methode oder Klasse: `abstract`

Methoden der Klasse Object
- Vergleich: `public boolean equals(Object obj)`
- HashCode: `public int hashCode()`
- Beschreibung

