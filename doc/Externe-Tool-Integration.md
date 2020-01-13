# Integration externer Tools

Zur Unterstützung des Entwicklungsworkflows sind bereits verschiedene externe Tools konfiguriert.

## Automatische Aktualisierung der Abhängigkeiten

Die Abhängigkeiten des Projektes (Dependencies) werden über Maven verwaltet und sind im [POM](../pom.xml) konfiguriert.
Um das Projekt immer auf dem laufenden Stand zu halten, ist die GitHub App [Dependabot](https://dependabot.com)
aktiv geschaltet: diese prüft automatisch, on neue Versionen einer Bibliothek (oder eines Maven Plugins) zur Verfügung 
stehen. Ist dies der Fall, erstellt der Roboter automatisch einen 
[Pull Request](https://github.com/uhafner/codingstyle/pulls), der die Version aktualisiert.

## Automatisierte Generierung eines Changelog

Damit der Nutzer des Projekts immer über alle Änderungen im Projekt im Bilde ist, ist die GitHub App 
[Release Drafter](https://github.com/toolmantim/release-drafter) aktiviert. Diese erstellt automatisch neue Changelog
Einträge im [GitHub Releases Bereich](https://github.com/uhafner/codingstyle/releases). Diese Einträge werden
aus den Titeln der Pull Requests generiert, d.h. jede Änderung am Projekt sollte über Pull Requests erfolgen und nicht 
über direkte Git Commits - dies entspricht auch dem Vorgehen des [GitHub Flows](https://guides.github.com/introduction/flow/).

## Statische Analyse von Pull Requests mit CheckStyle und PMD

Wie bereits im Abschnitt [Continuous Integration](Continuous-Integration.md) erwähnt, gibt es keinen öffentlichen 
Service, ein GitHub Projekt mit Jenkins automatisiert zu bauen. Für GitHub Actions gibt es diesen Service, aber 
die Visualisierung der Ergebnisse der statischen Code Analyse wird leider nicht unterstützt. Daher wird jeder Pull Request 
durch die externe App [Codacy](https://www.codacy.com/app/uhafner/codingstyle?utm_source=github.com&amp) überprüft: diese
verwendet dabei die gleichen CheckStyle und PMD Regeln. 

## Security Analyse von Pull Requests

Zusätzlich zur statischen Analyse wird ein Pull Request auf Sicherheitslücken untersucht. Dies erfolgt über die GitHub
App [LGTM](https://lgtm.com).

## Bewertung der Code Coverage 

Die Code Coverage der Unit Tests wird ebenfalls nach jeder Änderung (bzw. für jeden Pull Request) an den Service
[Codecov](https://codecov.io/gh/uhafner/codingstyle) weitergeleitet, der die Resultate grafisch visualisiert.
