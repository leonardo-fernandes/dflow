#!/bin/bash

byaccj -J -Jnoconstruct -Jnorun -Jpackage=org.dflow.compiler.parser -Jclass=ApplicationParser -Jthrows=IOException,ParseException Application.y
mv ApplicationParser.java ApplicationParserVal.java "$(dirname $0)/../src/org/dflow/compiler/parser/"

byaccj -J -Jnoconstruct -Jnorun -Jpackage=org.dflow.compiler.parser -Jclass=EntityParser -Jthrows=IOException,ParseException Entity.y
mv EntityParser.java EntityParserVal.java "$(dirname $0)/../src/org/dflow/compiler/parser/"

