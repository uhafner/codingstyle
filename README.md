![JDK8](https://img.shields.io/badge/jdk-8-yellow.svg)
[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Travis](https://img.shields.io/travis/uhafner/codingstyle/master.svg?logo=travis&label=travis%20build&logoColor=white)](https://travis-ci.org/uhafner/codingstyle)
[![Codecov](https://img.shields.io/codecov/c/github/uhafner/codingstyle.svg)](https://codecov.io/gh/uhafner/codingstyle)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a4f98b07b95c47c19eb3443ee90168cd)](https://www.codacy.com/app/uhafner/codingstyle?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=uhafner/codingstyle&amp;utm_campaign=Badge_Grade)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/uhafner/codingstyle.svg)](https://github.com/uhafner/codingstyle/pulls)

In jedem Java Projekt sollte der gesamte Quelltext die gleichen Kriterien bei Stil, Formatierung, etc.
verwenden. In diesem Projekt werden die Kodierungsrichtlinien zu meinen Vorlesungen an der Hochschule
München zusammengefasst. 

Dieses Projekt enthält neben der Dokumentation der wichtigsten Kodierungsrichtlinien auch gleichzeitig eine sinnvolle 
Konfiguration aller kostenlos verfügbaren statischen Codeanalyse Tools für Maven. Diese dort enthaltenen und automatisch 
prüfbaren Richtlinien werden - soweit wie möglich - nicht mehr extra im Text erwähnt. Damit kann diese Projekt gleichzeitig als
Vorlage für neue Projekte genutzt werden.
- [Checkstyle](https://checkstyle.org)
- [PMD](https://pmd.github.io/)
- [SpotBugs](https://spotbugs.github.io)
- [Error Prone](https://errorprone.info)
- [IntelliJ](https://www.jetbrains.com/help/idea/code-inspection.html)

Die automatisch prüfbaren Richtlinien können teilweise direkt als Warnungen in der Entwicklungsumgebung 
[IntelliJ](https://www.jetbrains.com/idea/) angezeigt werden (IntelliJ Inspections sowie Checkstyle und Error Prone 
nach Installation der entsprechenden Plugins). Somit ist sichergestellt,
dass immer die gleichen Warnungen angezeigt werden - egal wie und wo die Java Dateien weiterverarbeitet werden. 
Für FindBugs und PMD ist der Umweg über das Build Management Tool [Maven](http://maven.apache.org/) erforderlich 
(die entsprechenden IntelliJ Plugins sind leider aus meiner Sicht noch nicht ausgereift genug). 
Die Verwendung von Maven hat zudem den Vorteil, dass die Ergebnisse hinterher leicht in den Continuous Integration Server 
[Jenkins](https://jenkins.io/) eingebunden werden können. 

Eine Beispielintegration in Jenkins ist auch bereits vorhanden.
Diese kann über `docker-compose up` im Hauptverzeichnis gestartet werden. Anschließend wird die
aktuelle Jenkins LTS Version mit allen benötigten Plugins in einem Docker Container gestartet und mit einem ebenso als 
Docker Container initialisierten Java Agent (**Achtung**: Java 8) verbunden, der die Builds ausführt. Unter 
[http://localhost:8080/job/Codingstyle/](http://localhost:8080/job/Codingstyle/) 
ist dann nach erfolgreichem Start von Jenkins der entsprechende Jenkins Job sichtbar. Der Zugang erfolgt
mit Benutzer `admin` und Passwort `admin`. Der job `Codingstyle` muss danach manuell gestartet werden,
die Ergebnisse der Tests, Code und Mutation Coverage sowie statischen Analyse werden dann automatisch
visualisiert. Die beiden Docker Container haben externe Volumes, die sich unterhalb des Ordners 
`docker/volumes` befinden.

Die Richtlinien sind in den Vorlesungen 2014/2015 entstanden und werden laufend ergänzt.
Aktuell bestehen diese aus den folgenden Abschnitten:

- [Formatierung](doc/Formatierung.md)
- [Namensgebung](doc/Namensgebung.md)
- [Kommentare](doc/Kommentare.md)
- [Testen](doc/Testen.md)
- [Testen von Schnittstellen und Basisklassen](doc/Abstract-Test-Pattern.md)
- [Fehlerbehandlung](doc/Fehlerbehandlung.md)
- [Best Practice](doc/Best-Practice.md)

Zur besseren Verdeutlichung der angesprochenen Themen sind diesem Projekt auch [Java Beispiele](src/) angefügt, 
die sich möglichst genau an diese Richtlinien halten.

Ideen und Inhalte für diesen Styleguide lieferten verschiedene Bücher, insbesondere aber das Buch 
"The Elements of Java Style" [1]. Diese Bücher sind allesamt wegweisend für die Softwareentwicklung und sind 
damit Pflichtlektüre für Berufstätige in der Softwareentwicklung:
- [1] "The Elements of Java Style", Vermeulen, Ambler, Bumgardner, Metz, Misfeldt, Shur und Thompson, Cambridge University Press, 2000
- [2] "The Pragmatic Programmer. From Journeyman to Master", Andrew Hunt, David Thomas, Ward Cunningham, Addison Wesley, 1999
- [3] "Code Complete: A Practical Handbook of Software Construction", Steve McConnell, Microsoft Press, 2004
- [4] "Clean Code: A Handbook of Agile Software Craftsmanship", Robert C. Martin, Prentice Hall, 2008
- [5] "Effective Java", Third Edition, Joshua Bloch, Addison Wesley, 2017
- [6] "Refactoring: Improving the Design of Existing Code", Martin Fowler, Addison Wesley, 1999 

Die gesamten Dokumente dieser Kodierungsrichtlinien unterliegen der
[Creative Commons Attribution 4.0 International Lizenz](http://creativecommons.org/licenses/by/4.0/). Der 
Quelltext aller Beispiele und Klassen unterliegt der [MIT Lizenz](http://opensource.org/licenses/MIT).
