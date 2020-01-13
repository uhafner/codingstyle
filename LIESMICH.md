![JDK8](https://img.shields.io/badge/jdk-8-yellow.svg)
[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub Actions](https://github.com/uhafner/codingstyle/workflows/GitHub%20Actions/badge.svg)](https://github.com/uhafner/codingstyle/actions)
[![Codecov](https://img.shields.io/codecov/c/github/uhafner/codingstyle.svg)](https://codecov.io/gh/uhafner/codingstyle)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a4f98b07b95c47c19eb3443ee90168cd)](https://www.codacy.com/app/uhafner/codingstyle?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=uhafner/codingstyle&amp;utm_campaign=Badge_Grade)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/uhafner/codingstyle.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/uhafner/codingstyle/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/uhafner/codingstyle.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/uhafner/codingstyle/alerts/)

In jedem Java Projekt sollte der gesamte Quelltext die gleichen Kriterien bei Stil, Formatierung, etc.
verwenden. In diesem Projekt werden die Kodierungsrichtlinien zu meinen Vorlesungen an der Hochschule
München zusammengefasst. 

Dieses Projekt enthält neben der Dokumentation der wichtigsten Kodierungsrichtlinien auch gleichzeitig eine sinnvolle 
Konfiguration aller für Java kostenlos verfügbaren statischen Codeanalyse Tools mittels Maven. Diese dort enthaltenen und automatisch 
prüfbaren Richtlinien werden - soweit wie möglich - nicht mehr extra im Text erwähnt. Damit kann diese Projekt gleichzeitig als
Vorlage für neue Projekte genutzt werden. Unterstützt werden aktuell folgende Tools:
- [Checkstyle](https://checkstyle.org)
- [PMD](https://pmd.github.io/)
- [SpotBugs](https://spotbugs.github.io)
- [Error Prone](https://errorprone.info)

Die automatisch prüfbaren Richtlinien können für CheckStyle und Error Prone auch direkt als Warnungen in der 
Entwicklungsumgebung [IntelliJ](https://www.jetbrains.com/idea/) angezeigt werden (nach der Installation des 
entsprechenden IntelliJ Plugins). Zusätzlich sind die 
[IntelliJ Code Inspections](https://www.jetbrains.com/help/idea/code-inspection.html) gemäß meiner Richtlinien konfiguriert. 
Aktuell können diese allerdings noch nicht automatisch im Build überprüft werden 
(siehe [#7](https://github.com/uhafner/codingstyle/issues/7)). Insgesamt ist damit sichergestellt,
dass immer die gleichen Warnungen angezeigt werden - egal wie und wo die Java Dateien weiterverarbeitet werden. 
Für SpotBugs und PMD ist der Umweg über das Build Management Tool [Maven](http://maven.apache.org/) erforderlich 
(die entsprechenden IntelliJ Plugins sind leider aus meiner Sicht noch nicht ausgereift genug bzw. verwenden eine separate Konfiguration). 
Die Verwendung von Maven hat zudem den Vorteil, dass die Ergebnisse hinterher leicht in den Continuous Integration Server 
[Jenkins](https://jenkins.io/) eingebunden werden können. Eine beispielhafte Integration in GitHub Actions und Jenkins ist auch bereits vorhanden. 
Diese ist im eigenen Abschnitt [Continuous Integration](doc/Continuous-Integration.md)
ausführlicher beschrieben. Ebenso sind mehrere externe Tools konfiguriert, die die Qualität der Pull Requests 
in diesem Repository bewerten, Details dazu sind im Abschnitt [Integration externer Tools](doc/Externe-Tool-Integration.md) 
beschrieben.  

Die Richtlinien sind in den Vorlesungen 2014/2015 entstanden und werden laufend ergänzt.
Aktuell bestehen diese aus den folgenden Abschnitten:

- [Formatierung](doc/Formatierung.md)
- [Namensgebung](doc/Namensgebung.md)
- [Kommentare](doc/Kommentare.md)
- Testen
    - [Allgemeine Tipps zum Testen](doc/Testen.md)
    - [State Based vs. Interaction Based Testing](doc/State-Based-Vs-Interaction-Based.md)
    - [Testen von Schnittstellen und Basisklassen](doc/Abstract-Test-Pattern.md)
- [Fehlerbehandlung](doc/Fehlerbehandlung.md)
- [Best Practice](doc/Best-Practice.md)

Zur besseren Verdeutlichung der angesprochenen Themen sind diesem Projekt auch [Java Beispiele](./src/) angefügt, 
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
- [7] "Java by Comparison", Simon Harrer, Jörg Lenhard, Linus Dietz, Pragmatic Programmers, 2018

Die gesamten Dokumente dieser Kodierungsrichtlinien unterliegen der
[Creative Commons Attribution 4.0 International Lizenz](https://creativecommons.org/licenses/by/4.0/). Der 
Quelltext aller Beispiele und Klassen unterliegt der [MIT Lizenz](https://opensource.org/licenses/MIT).
