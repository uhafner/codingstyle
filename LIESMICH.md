[![GitHub Actions](https://github.com/uhafner/codingstyle/workflows/GitHub%20CI/badge.svg)](https://github.com/uhafner/codingstyle/actions)
[![CodeQL](https://github.com/uhafner/codingstyle/workflows/CodeQL/badge.svg)](https://github.com/uhafner/codingstyle/actions/workflows/codeql.yml)
[![Line Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/line-coverage.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor-comment.yml)
[![Branch Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/branch-coverage.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor-comment.yml)
[![Mutation Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/mutation-coverage.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor-comment.yml)
[![Warnings](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/style.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor-comment.yml)
[![Bugs](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/bugs.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor-comment.yml)

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
Quelltext aller Beispiele und Klassen unterliegt der [MIT Lizenz](https://en.wikipedia.org/wiki/MIT_License).
