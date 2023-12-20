[![GitHub Actions](https://github.com/uhafner/codingstyle/workflows/GitHub%20CI/badge.svg)](https://github.com/uhafner/codingstyle/actions)
[![CodeQL](https://github.com/uhafner/codingstyle/workflows/CodeQL/badge.svg)](https://github.com/uhafner/codingstyle/actions/workflows/codeql.yml)
[![Line Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/line-coverage.svg)](https://app.codecov.io/gh/uhafner/codingstyle)
[![Branch Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/branch-coverage.svg)](https://app.codecov.io/gh/uhafner/codingstyle)
[![Mutation Coverage](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/mutation-coverage.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor.yml)
[![Warnings](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/style.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor.yml)
[![Bugs](https://raw.githubusercontent.com/uhafner/codingstyle/main/badges/bugs.svg)](https://github.com/uhafner/codingstyle/actions/workflows/quality-monitor.yml)

Each Java project should follow a given coding style. I.e., all contributions to the source code should use the same 
formatting rules, design principles, code patterns, idioms, etc. This coding style provides the set of rules that I 
am using in my lectures about software development at Munich University of Applied Sciences.  

This project describes the coding style in detail (currently only available in German) and serves as a template project. 
It provides all necessary resources for a Java project to enforce this coding style using the following 
static analysis tools via Maven (and partly in IntelliJ):
- [Checkstyle](https://checkstyle.org)
- [PMD](https://pmd.github.io/)
- [SpotBugs](https://spotbugs.github.io)
- [Error Prone](https://errorprone.info)

❗This project requires a JDK version of 11 or higher.❗  

Moreover, this project provides some sample classes that already use this style guide. These classes can be used 
as such but are not required in this project. These classes also use some additional libraries that are included
using the Maven dependency mechanism. If the sample classes are deleted, then the dependencies can be safely 
deleted, too.

This project and the associated static analysis tools are already running in continuous integration: an example 
CI pipeline is active for GitHub Actions. For [Jenkins](https://jenkins.io/) a full CI pipeline has been 
configured that includes stages to compile, test, run static code analysis, run code coverage analysis, 
and run mutation coverage analysis, see section [Continuous Integration](doc/Continuous-Integration.md) for details. 
Additionally, some development tools are configured in this GitHub project to evaluate the quality of pull requests, 
see section [integration of external tools](doc/Externe-Tool-Integration.md).

Content of the style guide (only in German):
- [Formatierung](doc/Formatierung.md)
- [Namensgebung](doc/Namensgebung.md)
- [Kommentare](doc/Kommentare.md)
- Testen
    - [Allgemeine Tipps zum Testen](doc/Testen.md)
    - [State Based vs. Interaction Based Testing](doc/State-Based-Vs-Interaction-Based.md)
    - [Testen von Schnittstellen und Basisklassen](doc/Abstract-Test-Pattern.md)
- [Fehlerbehandlung](doc/Fehlerbehandlung.md)
- [Best Practice](doc/Best-Practice.md)

A lot of ideas in this style are based on the following path-breaking books about software development: 

- [1] "The Elements of Java Style", Vermeulen, Ambler, Bumgardner, Metz, Misfeldt, Shur und Thompson, Cambridge University Press, 2000
- [2] "The Pragmatic Programmer: journey to mastery", Second Edition, Andrew Hunt, David Thomas, Addison Wesley, 2019
- [3] "Code Complete: A Practical Handbook of Software Construction", Steve McConnell, Microsoft Press, 2004
- [4] "Clean Code: A Handbook of Agile Software Craftsmanship", Robert C. Martin, Prentice Hall, 2008
- [5] "Effective Java", Third Edition, Joshua Bloch, Addison Wesley, 2017
- [6] "Refactoring: Improving the Design of Existing Code", Martin Fowler, Addison Wesley, 1999 
- [7] "Java by Comparison", Simon Harrer, Jörg Lenhard, Linus Dietz, Pragmatic Programmers, 2018

All documents in this project use the 
[Creative Commons Attribution 4.0 International License](https://creativecommons.org/licenses/by/4.0/). 
Source code (snippets, examples, and classes) are using the [MIT license](https://en.wikipedia.org/wiki/MIT_License).

[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://en.wikipedia.org/wiki/MIT_License)
[![License: CC BY 4.0](https://img.shields.io/badge/License-CC%20BY%204.0-lightgrey.svg)](https://creativecommons.org/licenses/by/4.0/)
