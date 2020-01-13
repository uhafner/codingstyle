# Continuous Integration des Coding Style

Gemäß des Grundsatzes **eat your own dogfood** ist dieser Coding Style bereits für die Continuous Integration
in [GitHub Actions](https://github.com/features/actions) und [Jenkins](https://jenkins.io) vorbereitet. 

## Maven Konfiguration

Sowohl für GitHub Actions als auch für Jenkins erfolgt die Automatisierung des Builds über Maven. Im zugehörigen 
[POM](../pom.xml) sind alle Versionen der benutzten Maven Plugins und der benötigten Abhängigkeiten über Properties
definiert, d.h. eine Aktualisierung lässt sich im entsprechenden Abschnitt leicht selbst durchführen bzw. über das 
Maven Kommando `mvn versions:update-properties` automatisch aktualisieren. U.a. sind die folgenden Plugins vorkonfiguriert:
- maven-compiler-plugin: konfiguriert die Java Version und legt alle Error Prone Regeln fest
- maven-javadoc-plugin: aktiviert die strikte Prüfung von JavaDoc Kommentaren
- maven-jar-plugin: legt einen Modulnamen fest, falls das Projekt in Java 9 oder höher verwendet wird. Außerdem wird
ein test-jar konfiguriert, so dass alle Tests (und abstrakte Testklassen) auch als Dependencies genutzt werden können.
- maven-pmd-plugin: prüft das Projekt mit PMD, die Regeln liegen in der Datei [pmd-configuration.xml](../etc/pmd-configuration.xml).
- maven-checkstyle-plugin: prüft das Projekt mit CheckStyle, die Regeln liegen in der Datei [checkstyle-configuration.xml](../etc/checkstyle-configuration.xml).
- spotbugs-maven-plugin: prüft das Projekt mit SpotBugs, alle Regeln werden verwendet mit den Ausnahmen definiert in der Datei [spotbugs-exclusion-filter.xml](../etc/spotbugs-exclusion-filter.xml).
- org.revapi: prüft ob die aktuelle Versionsnummer die [semantische Versionierung](https://semver.org) berücksichtigt (source and binary). D.h. es gilt:
    1. Eine neue **Major** Version wurde definiert, wenn das API nicht mehr abwärtskompatibel ist.
    2. Eine neue **Minor** Version wurde definiert, wenn eine neue Funktionalität abwärtskompatibel hinzugefügt wurde.
    3. Eine neue **Patch** Version wurde definiert, wenn Fehler abwärtskompatibel behoben wurden.
- maven-surefire-plugin: aktiviert das Erkennen der Annotationen der Architekturtests mit [ArchUnit](https://www.archunit.org)
- jacoco-maven-plugin: misst die Code Coverage der Testfälle mit [JaCoCo](https://www.jacoco.org)
- pitest-maven: misst die Mutation Coverage der Testfälle mit [PITest](http://pitest.org)

## GitHub Actions

[![GitHub Actions](https://github.com/uhafner/codingstyle/workflows/GitHub%20Actions/badge.svg)](https://github.com/uhafner/codingstyle/actions)

Die Konfiguration der Continuous Integration in GitHub Actions is sehr [einfach](../.github/workflows/maven.yml). 
Da der gesamte Build über Maven automatisiert ist, besteht die Konfiguration eigentlich nur aus einem Maven Aufruf,
der das Projekt baut, alle Tests (Unit und Integrationstests) ausgeführt, die statische Code Analyse durchführt
und schließlich werden nochmals alle Test mit dem Code Coverage Tool JaCoCo analysiert. Bei einem erfolgreichen 
Build werden die Code Coverage Ergebnisse in die Platform [CodeCov](https://codecov.io/gh/uhafner/codingstyle) hochgeladen.
GitHub Actions bietet die Möglichkeit, Matrix Builds durchzuführen: d.h., der Build wird auf den Plattformen Linux, 
Windows und macOS parallel durchgeführt.

## Jenkins

Eine Beispielintegration in Jenkins ist auch bereits vorhanden. Diese ist im [Jenkinsfile](../Jenkinsfile) hinterlegt
und startet die Integration in mehreren Schritten (Stages). Zunächst werden auch hier alle Schritte wie in GitHub Actions
aufgerufen. Anschließend erfolgt noch ein Start der Mutation Coverage mit [PIT](http://pitest.org). Insgesamt ist
die CI Konfiguration für Jenkins umfangreicher, da nicht nur der eigentliche Build konfiguriert wird, sondern
auch die Darstellung der Ergebnisse im Jenkins UI über die entsprechenden Jenkins Plugins konfiguriert wird.
Eine solche Visualisierung ist unter GitHub Actions nicht verfügbar. 

### Lokale CI in Jenkins (über Docker Compose)

Da es für Jenkins keinen öffentlichen Service wie bei GitHub Actions gibt, um eigene Projekte zu bauen, muss die Jenkins 
Integration lokal durchgeführt werden. Zur Vereinfachung des Jenkins Setup ist in diesem Coding Style eine
lauffähige Jenkins Installation enthalten (im Sinne von *Infrastructure as Code*). 
Diese kann über `jenkins.sh` im Hauptverzeichnis gestartet werden. Anschließend wird die
aktuelle Jenkins LTS Version mit allen benötigten Plugins in einem Docker Container gebaut und gestartet (das dauert
beim ersten Aufruf etwas). Dazu wird ebenso ein als Docker Container initialisierter Java Agent (**Achtung**: Java 8) 
verbunden, der die Builds ausführt. Nach einem erfolgreichem Start von Jenkins ist dann unter 
[http://localhost:8080/job/Codingstyle/](http://localhost:8080/job/Codingstyle/) 
der entsprechende Jenkins Job sichtbar. Der Zugang auf diesen lokalen Rechner erfolgt zur Vereinfachung 
mit Benutzer `admin` und Passwort `admin`, anschließend hat man volle Jenkins Administrationsrechte. 
Der Job `Codingstyle` muss danach manuell gestartet werden,
die Ergebnisse der Tests, Code und Mutation Coverage sowie statischen Analyse werden dann automatisch
visualisiert. Das Jenkins Home Verzeichnis ist im Docker Container als externes Volume angelegt: d.h. der Zugriff kann
auf dem Host direkt im Verzeichnis `docker/volumes/jenkins-home` erfolgen.

Nach einem ersten Build in Jenkins sollte sich dann folgendes Bild ergeben:

![Jenkins Build Summary](images/build-result.png)
