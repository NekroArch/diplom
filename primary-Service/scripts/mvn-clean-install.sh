#!/bin/bash
mvn -f .. clean -Dmaven.clean.failOnError=false; mvn -f .. install -DskipTests=true
