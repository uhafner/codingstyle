String-Formatierung, Konvertierungen
- Erstellen von Strings mit `String.format(String format, Object... arguments)`
  - `%n` ist Zeilenumbruch
  - `%s` Platzhalter für String
  - `%d` Platzhalter für Ganzzahl, `%03d` mit drei Stellen und führender Null
  - `%f` Platzhalter für Fließkommazahl, `%.3f` mit 3 Nachkommastellen
- String zu Ganzzahl: `int number = Integer.parseInt(String number)` (kann Exception werfen)
- Ganzzahl zu String: `String text = String.valueOf(int number)`
- String zu Fließkommazahl: `double number = Double.parseDouble(String number)` (kann Exception werfen)
- Fließkommazahl zu String: `String text = String.valueOf(double number)`
- Direkte Ausgabe mit `System.out.printf(String format, Object... arguments)`

Statische Methoden der Klasse Math
- `Math.max(a, b)`, `Math.min(a, b)`, `Math.abs(a)`: Maximum, Minimum, Absolutwert
- `Math.round(a)`: rundet `double` nach `long`, bzw. `float` nach `int` (kaufmännisch)
- `Math.pow(a, b)`: berechnet a hoch b
- `Math.sqrt(a)`: berechnet die Wurzel

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
| Ausgabe    | `Arrays.toString(array)`              | `list.toString()`                |
| Vergleich  | `Arrays.equals(array, other)`         | `list.equals(other)`             |

Listen vs. Sets

| Operation     | Set (HashSet oder TreeSet)  | List (ArrayList oder LinkedList) |
|---------------|-----------------------------|----------------------------------|
| Definition    | `Set<Type> set`             | `List<Type> list`                |
| Erstellen     | `set = new HashSet<Type>()` | `list = new ArrayList<Type>()`   |
| Lesen         | nicht möglich               | `Type element = list.get(index)` |
| Größe         | `set.size()`                | `list.size()`                    |
| Überschreiben | nicht möglich               | `list.set(index, element)`       |
| Hinzufügen    | `set.add(element)`          | `list.add(element)`              |
|               | `set.addAll(elements)`      | `list.addAll(elements)`          |
| Einfügen      | nicht möglich               | `list.add(index, element)`       |
| Entfernen     | `set.remove(element)`       | `list.remove(element)`           |
|               | `set.removeAll(elements)`   | `list.removeAll(elements)`       |
|               |                             | `list.remove(index)`             |
| Leeren        | `set.clear()`               | `list.clear()`                   |
| Suchen        | `set.contains(element)`     | `list.contains(element)`         |
|               |                             | `list.indexOf(element)`          |
| Ausgabe       | `set.toString() `           | `list.toString() `               |
| Iterator      | `set.iterator() `           | `list.iterator() `               |

Statische Methoden der Klasse Collections

- Sortieren: 
  - `Collections.sort(List<Type> list)` (`Type` muss `Comparable` sein)
  - `Collections.sort(List<Type> list, Comparator<Type> c)`
- Reihenfolge umdrehen: `Collections.reverse(List<Type> list)`
- Mit Wert füllen: `Collections.fill(List<Type> list, Type value)`
- Wert suchen: `int Collections.binarySearch(List<Type> list, Type key)` (`Type` muss `Comparable` sein)

Methoden rund um Strings

- Suchen:
  - `boolean contains(String pattern)`
  - `boolean startsWith(String pattern)`
  - `boolean endsWith(String pattern)`
  - `int indexOf(String pattern)`
- Größe: `length()`, `isEmpty()`, `isBlank()`
- Ersetzen: `String replace(String pattern, String replacement)`
- Teilbereich: 
  - `String substring(int from, int to)` (Index)
  - `String StringUtils.substringBetween(String str, String open, String close)` (Text)
- Zusammenfügen: `String join(String delimiter, String... texts)`
- `String strip()`, `String toUpperCase()`, `String toLowerCase()`
- String in Zeichenarray umwandeln: `char[] toCharArray()`
- Vergleichen (lexikografisch): `int compareTo(String other)`
- Suchen: `boolean StringUtils.containsAnyIgnoreCase(String value, String... patterns)`
- Splitten: `String[] StringUtils.split(String separator)`

Statische Methoden der Klasse Character
- `boolean Character.isLetter(char c)` 
- `boolean Character.isDigit(char c)`
- `boolean Character.isWhitespace(char c)`
- `boolean Character.isUpperCase(char c)` 
- `boolean Character.isLowerCase(char c)`
- `for (char ch : text.toCharArray()) { ... }`

Testen
- Markierung einer Testmethode: `@Test`
- Assertions
  - `assertThat(Type actual).isSame(Type expected)`
  - `assertThat(Type actual).isEqualTo(Type expected)` (equals überschrieben)
  - `assertThat(Type actual).usingRecursiveComparison().isEqualTo(Type expected)` (sonst)
  - `assertThat(boolean actual).isTrue()` oder `assertThat(boolean expected).isFalse()`
  - `assertThat(String actual).contains("o").startsWith("Hello").endsWith("World")`
  - `assertThat(Collection<Type> actual).contains(Type expected1, Type expected2, ...)`
  - `assertThat(Collection<Type> actual).containsExactly(Type expected1, Type expected2, ...)`
  - `assertThat(Collection<Type> actual).first().isEqualTo(Type expected)`
  - `assertThatExceptionOfType(Type.class).isThrownBy(() -> code).withMessageContaining("Error");`

Exceptions
- Werfen mit `throw new Type("Meldungstext mit Kontext")`
- Testen mit: `assertThatExceptionOfType(Type.class).isThrownBy(() -> [CODE]).withMessageContaining("Error");`
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
- Beschreibung: `public String toString()`

Wichtige Schnittstellen

- Comparable: `public int compareTo(Type other)`
- Comparator: `public int compare(Type o1, Type o2)`
- Iterable: `public Iterator<Type> iterator()`
- Iterator: `public boolean hasNext()`, `public Type next()`

Maps
- Definition: `Map<KeyType, ValueType> map`
- Erstellen: `map = new HashMap<KeyType, ValueType>()`
- Hinzufügen: `map.put(KeyType key, ValueType value)`
- Lesen: `ValueType value = map.get(KeyType key)`
- Entfernen: `ValueType value = map.remove(KeyType key)`
- Größe: `map.size()`
- Leeren: `map.clear()`

