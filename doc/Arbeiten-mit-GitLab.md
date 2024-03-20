Disclaimer: Die folgende Anleitung ist unter großer Hilfe der folgenden beiden Tutorials entstanden:
- [How To Create a Pull Request on GitHub](https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github)
- [GitHub Standard Fork & Pull Request Workflow](https://gist.github.com/Chaser324/ce0505fbed06b947d962)

# Arbeiten mit GitLab

Für Abgaben zu allen meinen Veranstaltungen benutze ich das vom LRZ betriebene [GitLab](https://gitlab.lrz.de/): GitLab bietet Studierenden **und** Lehrenden eine einfache Möglichkeit, Aufgaben für Praktika in einem Git Projekt zu verwalten. Die Nutzung bietet folgende Vorteile:
- Sie lernen die gleiche Arbeitsweise kennen, die auch in der Industrie und vielen Open-Source-Projekten verwendet wird. So sind Sie ideal auf die Praxis vorbereitet. 
- Sie haben eine ausgereifte Oberfläche, mit der Sie Ihre Abgaben verwalten können:
  - Darstellung von Commits
  - Reviews von Merge Requests
  - Nachverfolgung von offenen Punkten und Fehlern
  - Automatische Builds
  - Automatische Sicherung durch Backups
- Ich habe eine einfache Möglichkeit, private Repositories auf Basis eines Templates für Abgaben zu erstellen. Die Aufgaben können sowohl als Einzel- oder Teamaufgabe konzipiert sein. Die Steuerung der Berechtigungen erfolgt automatisch.

Die Voraussetzung zur Nutzung von GitLab in unserer Veranstaltung ist der normale Account unserer Hochschule. Dieser Account wird automatisch für Sie eingerichtet, wenn Sie sich an der Hochschule einschreiben. Ich verwende die E-Mail-Adresse, die Sie von der Hochschule erhalten, um Sie in das jeweilige GitLab Projekt einzuladen.

Je nach Aufgabenstellung und Kurs, erhalten Sie pro Abgabe eigene Projekte oder ein Projekt, das über mehrere Abgaben geht. In jedem Fall erhalten Sie (oder Ihr Team) dann ein eigenes Git Repository, indem Sie alle Dateien (Dokumente, Programmtexte, etc.) Ihrer Abgaben hinzufügen. Die Repositories sind immer als privat markiert, d.h nur Sie (und ggf. Ihre Teammitglieder) können den Inhalt sehen und verändern. Im ersten Semester können Sie zum Hochladen die GitLab Oberfläche nutzen. Je erfahrener Sie werden, umso schneller können Sie den direkten Zugang über die Versionsverwaltung Git nutzen, das macht dann vieles einfacher und komfortabler. 

Wenn Sie weitere Fragen zu GitLab haben, nutzen Sie bitte auch die [Online Hilfe](https://docs.gitlab.com). Fragen können Sie auch direkt im Praktikum (oder im jeweiligen Matrix Kanal der Veranstaltung) stellen. In den nachfolgenden Abschnitten sind die wichtigsten Punkte kurz zusammengefasst.

## Mit dem Repository arbeiten

Damit die Aufgaben bewertet werden können, müssen Sie Ihren Quelltext in das GitLab Repository hochladen. Git ist ein [verteiltes Versionsmanagement System](https://git-scm.com/book/de/v2/Verteiltes-Git-Verteilter-Arbeitsablauf), d.h. Sie finden eine Kopie dieses Repositories (neben dem Original auf GitLab auf Ihrem Rechner und auf den Rechnern Ihrer Teammitglieder.

### Über die Entwicklungsumgebung das Projekt verwalten

Am einfachsten nutzen Sie nur die Entwicklungsumgebung IntelliJ, um Ihr Projekt mit GitLab zu synchronisieren. 

#### Import mit der Entwicklungsumgebung

Nach dem Start der Entwicklungsumgebung haben Sie die Möglichkeit, Ihr Projekt direkt zu importieren: Mit der Aktion **Get from VCS** (im Startup Wizard) oder dem Menüpunkt **File->New->Project from Version Control...** lässt sich das GitLab Projekt automatisch nach IntelliJ importieren. IntelliJ kümmert sich ab dann automatisch über die Verbindung zu GitLab. Kopieren Sie dazu Ihr Repository Link aus dem Classroom im Dialog in das Feld **Repository URL->URL** und bestätigen Sie den Import mit **Clone**. Beachten Sie, dass Sie sich beim Import über HTTPS authentifizieren müssen. Dazu müssen Sie ein Personal Access Token in GitLab erzeugen, die Authentifizierung via Browser funktioniert aktuell nicht. 

Nachdem das Projekt auf Ihren Rechner übertragen wurde, wird es in IntelliJ importiert und gebaut. Je nach bestehender Konfiguration müssen Sie dazu noch im Konfigurationsdialog **File->Project Structure->SDKs** ein JDK 21 unter dem Namen "21" anlegen und referenzieren. Ist das erledigt, können Sie die Tests in Ihrem Projekt starten. Dazu können Sie auf das Projekt mit Rechts klicken und **Run All Tests** auswählen. In den meisten Projekten habe ich noch eine spezielle **All Tests** Run-Konfiguration angelegt, die Sie auch direkt nutzen können.

#### Hochladen von Änderungen

Nach Veränderung Ihrer Dateien werden diese farblich in IntelliJ markiert. Mit dem Kommando **Git->Commit** spielen Sie Ihre Änderungen zurück nach GitLab. Geben Sie im dann im neuen Dialog eine Commitbeschreibung ein (was haben Sie geändert) und bestätigen Sie den Dialog mit **Commit and Push**. IntelliJ führt dann einen lokalen **Commit** aus und speichert Ihre Änderung im Git Repository **lokal**. Damit die Änderungen auch in GitLab sichtbar werden, wird anschließend Ihr komplettes Repository nach GitLab übertragen. 

### Mit der Kommandozeile arbeiten

Alternativ können Sie für diese Schritte auch die Kommandozeile nutzen. Dazu sind die in den nachfolgenden Abschnitten beschriebenen Schritte erforderlich. 

#### Das Repository auf den eigenen Rechner holen

Zunächst müssen Sie das Repository auf den eigenen Rechner holen. Git ist ein [verteiltes Versionsmanagement System](https://git-scm.com/book/de/v2/Verteiltes-Git-Verteilter-Arbeitsablauf), d.h. Sie finden eine Kopie dieses Repositories (neben dem Original auf GitLab) auf Ihrem Rechner und auf den Rechnern Ihrer Teampartner. Diese Kopie kann mit folgendem Kommando auf den eigenen Rechner geholt werden: 

```shell
# Clone your repository to your local machine using SSH
git clone git clone git@gitlab.lrz.de:dev/courses/summer-2024/arc/assignment1/assignment1-gruppe1.git 
```

Falls noch kein [SSH Schlüssel auf GitLab](https://docs.gitlab.com/ee/user/ssh.html) hinterlegt ist, lässt sich das alternativ auch mit HTTPS erledigen:

```shell
# Clone your repository to your local machine using HTTPS
git clone https://gitlab.lrz.de/dev/courses/summer-2024/arc/assignment1/assignment1-gruppe1.git 
```

Für die einfache passwort-freie Nutzung von GitLab empfehle ich die [Einrichtung von SSH](https://docs.gitlab.com/ee/user/ssh.html) möglichst schnell nachzuholen.

### Eigene Änderungen entwickeln

Für jede Abgabe (und für jede noch so kleine Änderung am Projekt), **muss** ein neuer Branch angelegt werden.
Die direkte Arbeit auf dem `main` Branch ist nicht sinnvoll und daher verboten. Dieses Vorgehen nennt sich "Feature-Branch-Workflow" und ist in der [Atlassian Bitbucket Hilfe](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow) umfassend beschrieben.

Um einen Branch anzulegen, sind folgende Schritte nötig:

```shell
# Checkout the main branch - you want your new branch to come from main
git checkout main

# Create a new branch named newfeature (give your branch its own simple informative name)
git branch newfeature

# Switch to your new branch
git checkout newfeature
```

Nun geht es ans Editieren bzw. Programmieren und alle Änderungen werden Schritt für Schritt erstellt. Hier hat sich das Test Driven Development bewährt, doch das soll nicht Teil dieser Anleitung sein (siehe [Kapitel Testen](Testen.md) in meinen Kodierungsrichtlinien).

Eine weitere sinnvolle Vorgehensweise ist das schrittweise Entwickeln: Die Entwicklung wird nicht in einem Rutsch durchgeführt und dann mit einem Commit abgeschlossen, sondern in mehreren Iterationen. Jeder Schritt, der fehlerfrei übersetzt werden kann und bei dem danach alle Tests durchlaufen, sollte einzeln mit einem Commit abgeschlossen werden. Dann lassen sich die Änderungen hinterher besser nachvollziehen. 

Beim Commit ist noch wichtig, eine gute Commit-Message zu vergeben, Chris Beam hat hierzu den hilfreichen Artikel [How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/) geschrieben, der dies gut erklärt. Das muss nicht so formal sein, wie dort beschrieben, hier sehen Sie einige Beispiele in meinem Projekt [codingstyle](https://github.com/uhafner/codingstyle/commits/main).

Je nach Aufgabenstellung gibt es im Projekt eine [GitLab Pipeline](https://docs.gitlab.com/ee/ci/pipelines/), die das Projekt nach jedem Commit neu baut und überprüft. Siehe dazu auch das [Kapitel Continuous Integration](Continuous-Integration.md) bzw. [Autograding](Autograding.md).  

## Merge Request vorbereiten und stellen

Sobald alle Änderungen lokal mit einem Commit abgeschlossen wurden, können diese in das GitLab Projekt integriert werden. Dazu ist lediglich ein Push erforderlich:
 
```shell
# Push the local branch newfeature to a remote branch in the forked repository (using the same name) 
git push --set-upstream origin newfeature
```

Nun sind diese Änderungen auch Online im eigenen GitLab Projekt sichtbar. GitLab erkennt dort automatisch, dass ein neuer Branch angelegt wurde und bietet eine entsprechende Schaltfläche in der Oberfläche an. Alternativ kann auch über den [Merge Request Dialog](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html) ein neuer Merge Request angelegt werden.      

Beim Anlegen des Merge Request muss nun ein Titel und eine Beschreibung eingegeben werden. Der Titel sollte den Namen der Aufgabe enthalten, die Beschreibung ggf. weitere Details dazu. Verwenden von Anrede, Grußformel oder Schlussformel sind nicht sinnvoll. 

Noch ein Hinweis in eigener Sache: Bitte weisen Sie den Merge Request **niemals** mir zu. Ebenso bitte **keine Review Wünsche** an mich eintragen, bei mehr als 50 Personen pro Veranstaltung und vielen Abgaben pro Semester wird es sonst schnell unübersichtlich. Ich bewerte Ihre Abgaben automatisch nach Ablauf der Abgabefrist. Das kann je nach Personenzahl auch einmal dauern.  
 
**Vor** dem finalen Anlegen des Merge Request muss geprüft werden, ob der Merge Request die gewünschten Änderungen enthält - und auch nur diese! Dazu den Abschnitt Files im Dialog öffnen und die einzelnen Änderungen durchgehen. Tauchen dort Änderungen auf, die nichts mit der Abgabe zu tun haben, so sind diese zu entfernen. Typischerweise sind dies Umformatierungen oder Leerzeilenänderungen an nicht beteiligten Abschnitten oder gar komplett andere Dateien (z.B. aus der Entwicklungsumgebung). 

Um solche Änderungen zu entfernen und damit den Merge Request zu säubern, müssen diese mit dem bereits beschriebenen Workflow umgesetzt werden: im Editor die Änderungen an den entsprechenden Dateien vornehmen, Commit lokal ausführen und dann wieder mit Push auf das GitLab Projekt bringen. 

Schaut der Merge Request dann wie gewünscht aus, so kann er mit *Create* erzeugt werden. Falls Sie noch nicht sicher sind, ob dieser PR fertig ist, können Sie diesen auch zunächst als *Draft* erstellen. Dann ist mir (und den Teammitgliedern) klar, dass der PR noch in Arbeit ist und kein abschließendes Feedback erwartet.  

### Den Merge Request aktualisieren

Sobald der Merge Request erzeugt wurde, wird dieser i.A. mit verschiedenen Tools automatisch überprüft. Welche Tools zum Tragen kommen, hängt individuell vom Projekt ab. Typischerweise wird eine [Continuous Integration](Continuous-Integration.md) gestartet, die einen Entwicklungs-Lebenszyklus ausführt:

1. Compile
2. Test
3. Analyze

Jeder dieser Schritte wird in GitLab mit einem *Ok* oder *Failed* Status markiert. Ist einer der Schritte mit *Failed* markiert, muss der Merge Request überarbeitet werden. Dazu muss der Fehler analysiert werden und dann der Quelltext an den passenden Stellen aktualisiert werden, sei es bei Compile- oder Testfehlern, bei Unterschreitung der geforderten Testabdeckung oder bei Verstößen gegen die Kodierungsrichtlinien.
Details dazu finden sich im Abschnitt [Autograding](Autograding.md).
