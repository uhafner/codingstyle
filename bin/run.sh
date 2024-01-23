#!/bin/sh

git pull
git push
mvn build-helper:parse-version
mvn -Dproject.version=1.0.0 com.github.ferstl:depgraph-maven-plugin:graph
