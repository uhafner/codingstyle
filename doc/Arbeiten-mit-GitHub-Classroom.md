Disclaimer: Die folgende Anleitung ist unter großer Hilfe der folgenden beiden Tutorials entstanden:
- [How To Create a Pull Request on GitHub](https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github)
- [GitHub Standard Fork & Pull Request Workflow](https://gist.github.com/Chaser324/ce0505fbed06b947d962)

# Arbeiten mit GitHub Classroom

GitHub Classroom bietet Studierenden und Lehrenden eine einfache Möglichkeit, Aufgaben für Praktika in einem Git 
Projekt zu verwalten. Die Nutzung bietet folgende Vorteile:
- Sie lernen die gleiche Arbeitsweise kennen, die auch in der Industrie und vielen Open-Source-Projekten verwendet wird. 
So sind Sie ideal auf die Praxis vorbereitet. Ich nutze beispielsweise GitHub seit mehr als 10 Jahren für meine 
[Jenkins Open Source Plugins](https://plugins.jenkins.io/ui/search?query=uhafner).
- Sie haben eine ausgereifte Oberfläche, mit der Sie Ihre Abgaben verwalten können:  
    - Darstellung von Commits
    - Reviews von Pull Requests
    - Nachverfolgung von offenen Punkten und Fehlern
    - Automatische Builds   

- Ich habe eine einfache Möglichkeit, private Repositories auf Basis eines Templates für Abgaben zu erstellen.

Wenn Sie weitere Fragen zu GitHub Classroom haben, nutzen Sie bitte auch die [Online Hilfe](https://classroom.github.com/help).
In den nachfolgenden Abschnitten sind die wichtigsten Punkte kurz zusammengefasst.

## Im Classroom einem Assignment beitreten

Der erste Schritt für eine Abgabe ist der Beitritt zum entsprechenden Classroom. Dazu müssen Sie das von mir versendete
Link anklicken und bestätigen. Wenn Sie noch keinen GitHub Account haben, ist das jetzt die Gelegenheit. Nach dem
Beitritt in den Classroom müssen Sie zunächst Ihren Namen aus der Liste der Teilnehmer auswählen. Damit helfen Sie
mir hinterher bei der Zuordnung von Hochschul- zu GitHub-Account. Die Namen habe ich aus dem ZPA exportiert, wenn Sie sich
nicht finden geben Sie mir bitte Bescheid (Sie können trotzdem schon beginnen).
    
Ist die Aufgabenstellung eine Teamaufgabe, dann müssen Sie sich Ihrem Team zuordnen. Dazu treten Sie dem bereits 
erstellten Team Ihres Partners (oder Ihrer Partner) bei. Falls noch kein dementsprechendes Team existiert, können Sie 
es neu anlegen. Sprechen Sie sich bitte vorher ab, wer das Team erstellt, sonst haben wir hinterher zu viele Teams. 

Als letzten Schritt müssen Sie dem Beitritt mit *Accept* bestätigen. Dann wird automatisch ein neues Repository
angelegt und mit meinem Template gefüllt. 

## Mit dem Repository arbeiten

Damit die Aufgaben bewertet werden können, müssen Sie Ihren Quelltext in das GitHub Repository hochladen. Git ist ein
[verteiltes Versionsmanagement System](https://git-scm.com/book/de/v2/Verteiltes-Git-Verteilter-Arbeitsablauf),
d.h. Sie finden eine Kopie dieses Repositories (neben dem Original auf GitHub) auf Ihrem Rechner und auf den Rechnern 
Ihrer Teampartner.

### Über die Entwicklungsumgebung das Projekt verwalten

Am einfachsten nutzen Sie nur die Entwicklungsumgebung IntelliJ, um Ihr Projekt mit GitHub zu synchronisieren. 

#### Import mit der Entwicklungsumgebung

Nach dem Start der Entwicklungsumgebung haben Sie die Möglichkeit, Ihr Projekt direkt zu importieren: Mit der Aktion **Get from VCS** 
(im Startup Wizard) oder dem Menüpunkt **File->New->Project from Version Control...**
lässt sich das GitHub Projekt automatisch nach IntelliJ importieren. IntelliJ kümmert sich ab dann automatisch über
die Verbindung zu GitHub. Kopieren Sie dazu Ihr Repository Link aus dem Classroom im Dialog in das Feld 
**Repository URL->URL** und bestätigen Sie den Import mit **Clone**. 

Nachdem das Projekt auf Ihren Rechner übertragen wurde, wird es in IntelliJ importiert und gebaut. Je nach bestehender 
Konfiguration müssen Sie dazu noch im Konfigurationsdialog **File->Project Structure->SDKs** ein JDK 11 unter dem Namen
"11" anlegen und referenzieren. Ist das erledigt, können Sie die Tests in Ihrem Projekt starten. Dazu können Sie auf
das Projekt mit Rechts klicken und **Run All Tests** auswählen. In den meisten Classroom Projekten habe ich noch
einen spezielle **All Tests** Runkonfiguration angelegt, die Sie auch direkt nutzen können.

#### Hochladen von Änderungen

Nach Veränderung Ihrer Dateien, werden diese farblich in IntelliJ markiert. Mit dem Kommando **Git->Commit** spielen Sie
Ihre Änderungen zurück nach GitHub. Geben Sie im dann im neuen Dialog eine Commitbeschreibung ein (was haben Sie geändert)
und bestätigen Sie den Dialog mit **Commit and Push**. IntelliJ führt dann einen lokalen **Commit** aus und speichert Ihre
Änderung im Git Repository **lokal**. Damit die Änderungen auch in GitHub sichtbar werden, wird anschließend Ihr 
komplettes Repository nach GitHub übertragen. 

### Mit der Kommandozeile arbeiten

Alternativ können Sie für diese Schritte auch die Kommandozeile nutzen. Dazu sind die in den nachfolgenden Abschnitten beschriebenen
Schritte erforderlich. 

#### Das Repository auf den eigenen Rechner holen

Zunächst müssen Sie das Repository auf den eigenen Rechner holen. Git ist ein 
[verteiltes Versionsmanagement System](https://git-scm.com/book/de/v2/Verteiltes-Git-Verteilter-Arbeitsablauf),
d.h. Sie finden eine Kopie dieses Repositories (neben dem Original auf GitHub) auf Ihrem Rechner und auf den Rechnern Ihrer
Teampartner. Diese Kopie kann mit folgendem Kommando auf den eigenen Rechner geholt werden: 

```shell
# Clone your repository to your local machine using SSH
git clone git@github.com:hafner-hm-edu/PROJECT.git
```

Falls noch kein 
[SSH Schlüssel auf GitHub](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/adding-a-new-ssh-key-to-your-github-account) 
hinterlegt ist, lässt sich das alternativ auch mit HTTPS erledigen:

```shell
# Clone your repository to your local machine using HTTPS
git clone https://github.com/hafner-hm-edu/PROJECT.git
```

Für die einfache passwort-freie Nutzung von GitHub empfehle ich die 
[Einrichtung von SSH](https://help.github.com/en/github/authenticating-to-github/connecting-to-github-with-ssh) 
möglichst schnell nachzuholen.

### Eigene Änderungen entwickeln

Für jede Abgabe (und für jede noch so kleine Änderung am Projekt), **muss** ein neuer Branch angelegt werden.
Die direkte Arbeit auf dem `master` Branch ist nicht sinnvoll und daher verboten.

Um einen Branch anzulegen, sind folgende Schritte nötig:

```shell
# Checkout the master branch - you want your new branch to come from master
git checkout master

# Create a new branch named newfeature (give your branch its own simple informative name)
git branch newfeature

# Switch to your new branch
git checkout newfeature
```

Nun geht es ans Editieren bzw. Programmieren und alle Änderungen werden Schritt für Schritt erstellt. 
Hier hat sich das Test Driven Development bewährt, doch das soll nicht Teil dieser Anleitung sein
(siehe [Kapitel Testen](Testen.md) in meinen Kodierungsrichtlinien).

Ein weitere sinnvolle Vorgehensweise ist das schrittweise Entwickeln: Die Entwicklung wird nicht in einem
Rutsch durchgeführt und dann mit einem Commit abgeschlossen, sondern in mehreren Iterationen. Jeder Schritt,
der fehlerfrei übersetzt werden kann und bei dem danach alle Tests durchlaufen, sollte einzeln mit einem
Commit abgeschlossen werden. Dann lassen sich die Änderungen hinterher besser nachvollziehen. 

Beim Commit ist noch wichtig, eine gute Commit-Message zu vergeben, Chris Beam hat hierzu den hilfreichen Artikel
[How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)
geschrieben, der dies gut erklärt. Das muss nicht so formal sein, wie dort beschrieben, hier sehen Sie einige
Beispiele in meinem Projekt [codingstyle](https://github.com/uhafner/codingstyle/commits/master).

Je nach Aufgabenstellung gibt es im Projekt eine [GitHub Action](https://docs.github.com/en/free-pro-team@latest/actions),
die das Projekt nach jedem Commit neu baut und überprüft. Siehe dazu auch das 
[Kapitel Continuous Integration](Continuous-Integration.md) bzw. [Autograding](Autograding.md).  

## Pull Request vorbereiten und stellen

Sobald alle Änderungen lokal mit einem Commit abgeschlossen wurden, können diese in das GitHub Projekt integriert
werden. Dazu ist lediglich ein Push erforderlich:
 
```shell
# Push the local branch newfeature to a remote branch in the forked repository (using the same name) 
git push --set-upstream origin newfeature
```

Nun sind diese Änderungen auch Online im eigenen GitHub Projekt sichtbar. GitHub erkennt dort automatisch,
dass ein neuer Branch angelegt wurde und bietet eine entsprechende Schaltfläche in der Oberfläche an.
Alternativ kann auch über den 
[Pull Request Dialog](https://docs.github.com/en/free-pro-team@latest/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request)
ein neuer Pull Request angelegt werden.      

Beim Anlegen des Pull Request muss nun ein Titel und eine Beschreibung eingegeben werden. Der Titel sollte den Namen
der Aufgabe enthalten, die Beschreibung ggf. weitere Details dazu. Verwenden von Anrede, Grußformel oder Schlussformel
sind nicht sinnvoll. 

Noch ein Hinweis in eigener Sache: Bitte weisen Sie den Pull Request **niemals** mir zu. Ebenso bitte 
**keine Review Wünsche** an mich eintragen, bei mehr
als 50 Teilnehmern pro Vorlesung und vielen Abgaben pro Semester wird es sonst schnell unübersichtlich. Ich bewerte Ihre
Abgaben automatisch nach Ablauf der Abgabefrist. Das kann je nach Teilnehmerzahl auch einmal dauern.  
 
**Vor** dem finalen Anlegen des Pull Request muss geprüft werden, ob der Pull Request die gewünschten Änderungen 
enthält - und auch nur diese! Dazu den Abschnitt Files im Dialog öffnen und die einzelnen Änderungen durchgehen. Tauchen dort 
Änderungen auf, die nichts mit der Abgabe zu tun haben, so sind diese zu entfernen. Typischerweise sind dies 
Umformatierungen oder Leerzeilenänderungen an nicht beteiligten Abschnitten oder gar komplett andere Dateien 
(z.B. aus der Entwicklungsumgebung). 

Um solche Änderungen zu entfernen und damit den Pull Request zu säubern, müssen diese mit dem bereits beschriebenen 
Workflow umgesetzt werden: im Editor die Änderungen an den entsprechenden Dateien vornehmen, Commit lokal ausführen und
dann wieder mit Push auf das GitHub Projekt bringen. 

Schaut der Pull Request dann wie gewünscht aus, so kann er mit *Create* erzeugt werden. Falls Sie noch nicht sicher sind,
ob dieser PR fertig ist, können Sie diesen auch zunächst als *Draft* erstellen. Dann ist mir (und den Teammitgliedern)
klar, dass der PR noch in Arbeit ist und kein abschließendes Feedback erwartet.  

### Den Pull Request aktualisieren

Sobald der Pull Request erzeugt wurde, wird dieser i.A. mit verschiedenen Tools automatisch 
überprüft. Welche Tools zum Tragen kommen, hängt individuell vom Projekt ab. Typischerweise wird eine 
[Continuous Integration](Continuous-Integration.md) gestartet, die einen Entwicklungs-Lebenszyklus ausführt:

1. Compile
2. Test
3. Analyze

Jeder dieser Schritte wird in GitHub mit einem *Ok* oder *Failed* Status markiert, ggf. auch über 
[GitHub Checks](https://docs.github.com/en/free-pro-team@latest/github/collaborating-with-issues-and-pull-requests/about-status-checks). Ist einer der Schritte mit *Failed*
markiert, muss der Pull Request überarbeitet werden. Dazu muss der Fehler analysiert werden und dann der Quelltext
an den passenden Stellen aktualisiert werden, sei es bei Compile- oder Testfehlern, bei Unterschreitung der geforderten 
Testabdeckung oder bei Verstößen gegen die Kodierungsrichtlinien.
