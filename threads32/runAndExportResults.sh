#!/bin/bash

JAR_NAME=threads32-1.0-SNAPSHOT.jar

mvn clean package
java -jar target/$JAR_NAME

