#!/bin/bash

mvn compile
LC_ALL=C LC_CTYPE=C LC_NUMERIC=C mvn exec:java -Dexec.mainClass=core.MainBase

