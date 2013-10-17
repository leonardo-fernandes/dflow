#!/bin/sh

byaccj -J -Jnoconstruct -Jnorun -Jpackage=org.dflow.compiler.parser -Jclass=DataModelParser -Jthrows=IOException,ParseException dflow.dml.y
mv DataModelParser.java DataModelParserVal.java ../src/org/dflow/compiler/parser/
