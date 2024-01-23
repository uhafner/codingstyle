#!/bin/sh

git pull
git push
mvn -B clean build-helper:parse-version release:prepare release:perform -DdevelopmentVersion=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.0-SNAPSHOT
mvn -Dproject.version=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.patchVersion} com.github.ferstl:depgraph-maven-plugin:graph scm:add -Dincludes=doc/dependency-graph.puml
