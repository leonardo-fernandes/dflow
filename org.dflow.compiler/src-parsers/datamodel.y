%language "Java"
%locations
%define package "org.dflow.compiler.parser"
%define public
%define final
%define parser_class_name "DataModelParser"

%code imports {
import org.dflow.compiler.parser.ast.*;
import org.dflow.compiler.parser.ast.datamodel.*;
import org.dflow.compiler.parser.ast.enumerate.*;
import org.dflow.compiler.parser.ast.identifiers.*;
import org.dflow.compiler.parser.ast.typereferences.*;
}

%code {
    private DflowEntityFile parsedFile;

    public DflowEntityFile getParsedFile() {
        return parsedFile;
    }
}

%parse-param {java.io.File file}

%token PACKAGE IMPORT
%token PUBLIC PRIVATE PROTECTED
%token APPLICATION ENTITY ENUM
%token <String> ID

%type <PackageDeclaration> package_declaration
%type <ImportDeclaration.Block> import_list
%type <ImportDeclaration> import_declaration
%type <Identifier> compound_identifier
%type <TypeReference> type_reference
%type <GenericTypeReference.Arguments> generic_type_arguments
%type <DflowFile> dflow_file
%type <Entity> entity_definition
%type <Entity.Contents> entity_contents
%type <EntityAttribute> attribute_definition
%type <Enumerate> enum_definition
%type <Enumerate.Contents> enum_contents
%type <Enumerate.Values> enum_values

%start dflow_file

%%

package_declaration : PACKAGE compound_identifier ';' { $$ = new PackageDeclaration($2); } ;

import_list : /* empty */ { $$ = new ImportDeclaration.Block(); }
            | import_list import_declaration { $$ = $1.add($2); } ;

import_declaration : IMPORT compound_identifier ';' { $$ = new ImportDeclaration($2); }
                   | IMPORT compound_identifier '.' '*' ';' { $$ = new ImportDeclaration($2, true); } ;

compound_identifier : ID { $$ = new SimpleIdentifier($1); } ;

type_reference : compound_identifier { $$ = new SimpleTypeReference($1); }
               | type_reference '[' ']' { $$ = new ArrayTypeReference($1); }
               | type_reference '<' generic_type_arguments '>' { $$ = new GenericTypeReference($1, $3); } ;

generic_type_arguments : type_reference { $$ = new GenericTypeReference.Arguments().add($1); }
                       | generic_type_arguments ',' type_reference { $$ = $1.add($3); } ;


dflow_file : package_declaration import_list entity_definition { this.parsedFile = new DflowEntityFile(this.file, $1, $2, $3); } ;

entity_definition : PUBLIC ENTITY ID '{' entity_contents '}' { $$ = new Entity($3, $5); } ;

entity_contents : /* empty */ { $$ = new Entity.Contents(); }
                | entity_contents attribute_definition { $$ = $1.addAttribute($2); }
                | entity_contents entity_definition { $$ = $1.addNestedEntity($2); }
                | entity_contents enum_definition { $$ = $1.addEnumerate($2); } ;

attribute_definition : type_reference ID ';' { $$ = new EntityAttribute($1, $2); } ;

enum_definition : PUBLIC ENUM ID '{' enum_contents '}' { $$ = new Enumerate($3, $5); } ;

enum_contents : enum_values { $$ = new Enumerate.Contents($1); }
              | enum_values ';' { $$ = new Enumerate.Contents($1); } ;

enum_values : ID { $$ = new Enumerate.Values().add(new Enumerate.Value($1)); }
            | enum_values ',' ID { $$ = $1.add(new Enumerate.Value($3)); } ;
