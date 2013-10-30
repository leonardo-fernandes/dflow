#!/bin/bash

jflex --nobak -d "$(dirname $0)/../src/org/dflow/compiler/parser/" dflow.l
bison -o "$(dirname $0)/../src/org/dflow/compiler/parser/ApplicationParser.java" application.y
bison -o "$(dirname $0)/../src/org/dflow/compiler/parser/DataModelParser.java" datamodel.y
