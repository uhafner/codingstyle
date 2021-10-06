Disclaimer: Die folgende Anleitung ist unter großer Hilfe der folgenden beiden Tutorials entstanden:
- [How To Create a Pull Request on GitHub](https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github)
- [GitHub Standard Fork & Pull Request Workflow](https://gist.github.com/Chaser324/ce0505fbed06b947d962)

# Arbeiten mit Pull Requests in GitHub

Abgaben in GitHub werden über Pull Requests gestellt. Dazu sind die in den nachfolgenden Abschnitten beschriebenen
Schritte erforderlich.

## Einen Fork erstellen

Wer einen Pull Request mit seinen Ergebnissen stellen will, braucht zunächst einen 
[Fork](https://help.github.com/en/github/getting-started-with-github/fork-a-repo) des entsprechenden Projektes. Dies
geht nicht mit der Kommandozeile, sondern nur in der GitHub Oberfläche, siehe 
[Anleitung](https://help.github.com/en/github/getting-started-with-github/fork-a-repo#fork-an-example-repository).
Dieser neu erstellte Fork ist zunächst eine exakte Kopie des Ausgangsprojektes, d.h. er enthält alle Commits,
Branches und Tags. 

## Mit dem Fork arbeiten

Sobald der Fork erstellt wurde, ist dieser unter dem eigenen GitHub Benutzerkonto als Kopie sichtbar. Diese Kopie kann
dann mit folgendem Kommando auf den eigenen Rechner geholt werden: 

```shell
# Clone your fork to your local machine using SSH
git clone git@github.com:USERNAME/FORKED-PROJECT.git
```
Falls noch kein SSH Schlüssel auf GitHub hinterlegt ist, lässt sich das alternativ auch mit HTTPS erledigen:

```shell
# Clone your fork to your local machine using HTTPS
git clone https://github.com/USERNAME/FORKED-PROJECT.git
```

Für die einfache passwort-freie Nutzung von GitHub empfehle ich die 
[Einrichtung von SSH](https://help.github.com/en/github/authenticating-to-github/connecting-to-github-with-ssh) 
möglichst schnell nachzuholen.

## Eigene Änderungen entwickeln

Für jede Abgabe (und für jede noch so kleine Änderung am Projekt), **muss** ein neuer Branch angelegt werden.
Die Arbeit auf dem `master` Branch ist nicht sinnvoll, siehe auch das [Kapitel "Fork aktuell halten"](#den-fork-aktuell-halten).

Um einen Branch anzulegen, sind folgende Schritte nötig:

```shell
# Checkout the master branch - you want your new branch to come from master
git checkout master

# Create a new branch named newfeature (give your branch its own simple informative name)
git branch newfeature

# Switch to your new branch
git checkout newfeature
```

Nun geht es ans Programmieren und alle Änderungen werden Schritt für Schritt erstellt. 
Hier hat sich das Test Driven Development bewährt, doch das soll nicht Teil dieser Anleitung sein
(siehe [Kapitel Testen](Testen.md) in meinen Kodierungsrichtlinien).

Ein weitere sinnvolle Vorgehensweise ist das schrittweise Entwickeln: Die Entwicklung wird nicht in einem
Rutsch durchgeführt und dann mit einem Commit abgeschlossen, sondern in mehreren Iterationen. Jeder Schritt,
der fehlerfrei übersetzt werden kann und bei dem danach alle Tests durchlaufen, sollte einzeln mit einem
Commit abgeschlossen werden. Dann lassen sich die Änderungen hinterher besser nachvollziehen. 

Beim Commit ist noch wichtig, eine gute Commit-Message zu vergeben, Chris Beam hat hierzu den hilfreichen Artikel
[How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)
geschrieben, der dies gut erklärt.

## Pull Request vorbereiten und stellen

Sobald alle Änderungen lokal mit einem Commit abgeschlossen wurden, können diese in den Fork auf GitHub integriert
werden. Dazu ist lediglich ein Push erforderlich:
 
```shell
# Push the local branch newfeature to a remote branch in the forked repository (using the same name) 
git push --set-upstream origin newfeature
```

Nun sind diese Änderungen auch Online im eigenen GitHub Projekt sichtbar. GitHub erkennt dort automatisch,
dass ein neuer Branch angelegt wurde und bietet eine entsprechende Schaltfläche in der Oberfläche an.
Alternativ kann auch über den 
[Pull Request Dialog](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request-from-a-fork)
ein neuer Pull Request angelegt werden.      

Beim Anlegen des Pull Request muss nun ein Titel und eine Beschreibung eingegeben werden. Der Titel sollte den Namen
der Aufgabe enthalten, die Beschreibung ggf. weitere Details dazu. Verwenden von Anrede, Grußformel oder Schlussformel
sind nicht sinnvoll. 

**Vor** dem finalen Anlegen des Pull Request muss geprüft werden, ob der Pull Request die gewünschten Änderungen 
enthält - und auch nur diese! Dazu den Abschnitt Files im Dialog öffnen und die einzelnen Änderungen durchgehen. Tauchen dort 
Änderungen auf, die nichts mit der Abgabe zu tun haben, so sind diese zu entfernen. Typischerweise sind dies Umformatierungen oder Leerzeilenänderungen
an nicht beteiligten Abschnitten oder gar komplett andere Dateien. 

Um solche Änderungen in den Pull Request zu integrieren, müssen diese mit dem bereits beschriebenen Workflow umgesetzt 
werden: im Editor die Änderungen an den entsprechenden Dateien vornehmen, Commit lokal ausführen und dann wieder
mit Push auf das GitHub Projekt bringen. 

Schaut der Pull Request dann wie gewünscht aus, so kann er mit *Create* erzeugt werden. 

## Den Pull Request aktualisieren

Sobald der Pull Request erzeugt wurde, wird dieser mit verschiedenen Tools automatisch überprüft. Welche Tools zum Tragen 
kommen, hängt individuell vom Projekt ab. Typischerweise wird eine [Continuous Integration](Continuous-Integration.md) 
gestartet, die einen Entwicklungs-Lebenszyklus ausführt:

1. Compile
2. Test
3. Analyze

Jeder dieser Schritte wird in GitHub mit einem *Ok* oder *Failed* Status markiert. Ist einer der Schritte mit *Failed*
markiert, muss der Pull Request überarbeitet werden. Dazu muss der Fehler analysiert werden und dann der Quelltext
an den passenden Stellen aktualisiert werden, sei es bei Compile- oder Testfehlern, bei Unterschreitung der geforderten 
Testabdeckung oder bei Verstößen gegen die Kodierungsrichtlinien.

Sind alle automatischen Tests auf *Ok*, fehlt nur noch das Review des Autors des Original Projektes. Dies erfolgt 
zeilenweise ebenfalls im Pull Request und kann mit den gleichen Schritten wie oben beschrieben eingearbeitet werden.
Normalerweise erkennt GitHub diese Änderungen automatisch, diese müssen daher nicht explizit als *Gelöst* markiert 
werden.   

## Den Fork aktuell halten

Wichtig ist, diesen Fork immer aktuell zu halten, d.h. die Änderungen des Ausgangsprojektes
immer nachzuziehen. In den meisten Fällen genügt es, den sogenannten `master` Branch synchron zu halten. 
Um dies zu ermöglichen, muss das Original Projekt als ein weiteres Remote hinzugefügt werden. Dazu hat sich der Name
`upstream` eingebürgert. Dies lässt sich mit folgendem Kommando umsetzen:

```shell
# Add 'upstream' repo to list of remotes
git remote add upstream https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git
```

Durch das folgende Kommando lässt sich die Konfiguration überprüfen:

```shell
# Verify the new remote named 'upstream'
git remote -v
```

Dann sollte folgende Ausgabe erfolgen:

```shell
origin	git@github.com:USERNAME/FORKED-PROJECT.git (fetch)
origin	git@github.com:USERNAME/FORKED-PROJECT.git (push)
upstream	https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git (fetch)
upstream	https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git (push)
```

Immer dann, wenn die Änderungen des `master` Branches vom Originalprojekt integriert werden sollen, können diese 
mit folgendem Kommando eingebunden werden:

```shell
# Fetch from upstream remote
git fetch upstream

# View all branches, including those from upstream
git branch -va

# Checkout your master branch and merge upstream
git checkout master
git merge upstream/master
```

Normalerweise sollte auf dem lokalen `master` Branch keine anderen Commits sein, daher wird ein 
[fast-forward](https://git-scm.com/book/de/v2/Git-Branching-Einfaches-Branching-und-Merging) angewendet. 
