# Integration externer Tools

Zur besseren Visualisierung der Projektergebnisse sind bereits verschiedene externe Tools konfiguriert.

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
