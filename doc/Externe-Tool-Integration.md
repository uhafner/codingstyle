# Integration externer Tools

Zur Unterstützung des Entwicklungsworkflows sind bereits verschiedene externe Tools konfiguriert.

## Automatische Aktualisierung der Abhängigkeiten

Die Abhängigkeiten des Projektes (Dependencies) werden über Maven verwaltet und sind im [POM](../pom.xml) konfiguriert.
Um das Projekt immer auf dem laufenden Stand zu halten, ist die GitHub App [Dependabot](https://dependabot.com) aktiv geschaltet.
Diese prüft automatisch, on neue Versionen einer Bibliothek (oder eines Maven Plugins) zur Verfügung stehen. 
Ist dies der Fall, erstellt der Roboter automatisch einen [Pull Request](https://github.com/uhafner/codingstyle/pulls), der die Version aktualisiert.

## Automatisierte Generierung eines Changelog

Damit die Nutzer des Projekts immer über alle Änderungen im Projekt im Bild sind, ist die GitHub App [Release Drafter](https://github.com/toolmantim/release-drafter) aktiviert. 
Diese erstellt automatisch neue Changelog Einträge im [GitHub Releases Bereich](https://github.com/uhafner/codingstyle/releases). 
Diese Einträge werden automatisch aus den Titeln der Pull Requests generiert, d. h. jede Änderung am Projekt sollte über Pull Requests erfolgen und nicht über direkte Git Commits – dies entspricht auch dem Vorgehen des [GitHub Flows](https://guides.github.com/introduction/flow/).

## Continuous Integration (inklusive Überwachung von Quality Gates)

Wie bereits im Abschnitt [Continuous Integration](Continuous-Integration.md) erwähnt, wird das Projekt bei jeder Änderung mit einer GitHub Pipeline automatisiert gebaut.
Diese [Pipeline](https://github.com/uhafner/codingstyle/blob/main/.github/workflows/ci.yml) läuft auf Rechnern von GitHub und führt einen Build unter JDK 21 und 25 aus. 
Aktuell wird der Build auf virtualisierten Rechnern der Betriebssysteme Linux, Windows und macOS durchgeführt.
Anschließend werden die Build Ergebnisse mit meinem [Quality Monitor](https://github.com/uhafner/quality-monitor) analysiert.
Dieser Service sammelt die Ergebnisse der statischen Code-Analyse (CheckStyle, PMD, SpotBugs) und der Code-Coverage (JaCoCo) und stellt diese als Kommentar im Pull Request dar.
Anschließend werden die Quality Gates überprüft: Werden die definierten Qualitätskriterien nicht erfüllt, so schlägt der Build fehl.

## Sicherheitsanalyse von Pull Requests

Zusätzlich zur statischen Analyse wird ein Pull Request auf Sicherheitslücken untersucht. Dies erfolgt über die GitHub App [CodeQL](https://codeql.github.com/).
