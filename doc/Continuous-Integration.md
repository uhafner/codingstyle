# Continuous Integration des Coding Style

Gemäß des Grundsatzes **eat your own dogfood** ist dieser Coding Style bereits für die Continuous Integration
in [Travis](https://travis-ci.org) und [Jenkins](https://jenkins.io) vorbereitet. 

## Travis

[![Travis](https://img.shields.io/travis/uhafner/codingstyle/master.svg?logo=travis&label=travis%20build&logoColor=white)](https://travis-ci.org/uhafner/codingstyle)

Die Konfiguration der Continuous Integration in Travis is sehr [einfach](../.travis.yml). Da der gesamt Build 
über Maven automatisiert ist, besteht die Konfiguration eigentlich nur aus dem Maven Aufruf 
`mvn -B -V clean verify jacoco:prepare-agent test jacoco:report`. Damit wird das Projekt gebaut, alle Tests (Unit und 
Integrationstests) werden ausgeführt, die statische Code Analyse wird durchgeführt und schließlich werden nochmals
alle Test mit dem Code Coverage Tool JaCoCo analysiert. Bei einem erfolgreichen Build werden die Code Coverage 
Ergebnisse in die Platform [CodeCov](https://codecov.io/gh/uhafner/codingstyle) hochgeladen. Da Travis OSS Projekte
kostenlos baut, kann das Ergebnis der CI des Coding Style auf der [Travis Projekt Seite](https://travis-ci.org/uhafner/codingstyle) direkt eingesehen werden.

## Jenkins

Eine Beispielintegration in Jenkins ist auch bereits vorhanden. Diese ist im [Jenkinsfile](../Jenkinsfile) hinterlegt
und startet die Integration in mehreren Schritten (Stages). Zunächst werden auch hier alle Schritte wie in Travis
aufgerufen. Anschließend erfolgt noch ein Start der Mutation Coverage mit [PIT](http://pitest.org). Insgesamt ist
die CI Konfiguration für Jenkins umfangreicher, da nicht nur der eigentliche Build konfiguriert wird, sondern
auch die Darstellung der Ergebnisse im Jenkins UI über die entsprechenden Jenkins Plugins konfiguriert wird.
Eine solche Visualisierung ist unter Travis nicht verfügbar. 

### Lokale CI in Jenkins (über Docker Compose)

Da es für Jenkins keinen öffentlichen Service wie bei Travis gibt, um eigene Projekte zu bauen, muss die Jenkins 
Integration lokal durchgeführt werden. Zur Vereinfachung des Jenkins Setup ist in diesem Coding Style eine
lauffähige Jenkins Installation enthalten (im Sinne von *Infrastructure as Code*). 
Diese kann über `docker-compose up` im Hauptverzeichnis gestartet werden. Anschließend wird die
aktuelle Jenkins LTS Version mit allen benötigten Plugins in einem Docker Container gebaut und gestartet (das dauert
beim ersten Aufruf etwas). Dazu wird ebenso ein als Docker Container initialisierter Java Agent (**Achtung**: Java 8) 
verbunden, der die Builds ausführt. Nach einem erfolgreichem Start von Jenkins ist dann unter 
[http://localhost:8080/job/Codingstyle/](http://localhost:8080/job/Codingstyle/) 
der entsprechende Jenkins Job sichtbar. Der Zugang erfolgt
mit Benutzer `admin` und Passwort `admin`. Der job `Codingstyle` muss danach manuell gestartet werden,
die Ergebnisse der Tests, Code und Mutation Coverage sowie statischen Analyse werden dann automatisch
visualisiert. Die beiden Docker Container haben externe Volumes, die sich unterhalb des Ordners 
`docker/volumes` befinden.
