#!make
ifeq ($(OS),Windows_NT)
SHELL := cmd
else
SHELL ?= /bin/bash
endif

#JAR_VERSION := $(shell mvn -q -Dexec.executable="echo" -Dexec.args='$${project.version}' --non-recursive exec:exec -DforceStdout)
JAR_VERSION := 1.0
JAR_FILE := ComputeCSS-$(JAR_VERSION).jar


all: target/$(JAR_FILE)


target/$(JAR_FILE):
	mvn -DskipTests clean package shade:shade

test:
	java -version
	mvn test


clean:
	mvn clean


.PHONY: all clean test deploy version
