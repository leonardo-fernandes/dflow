%language "Java"
%locations
%define package "org.dflow.compiler.parser"
%define public
%define final
%define parser_class_name "ApplicationParser"

%code imports {
import org.dflow.compiler.parser.ast.*;
import org.dflow.compiler.parser.ast.application.*;
import org.dflow.compiler.parser.ast.identifiers.*;
import org.dflow.compiler.parser.ast.typereferences.*;
}

%code {
    private DflowApplicationFile parsedFile;

    public DflowApplicationFile getParsedFile() {
        return parsedFile;
    }
}

%parse-param {java.io.File file}

%token PACKAGE IMPORT
%token PUBLIC PRIVATE PROTECTED
%token APPLICATION ENTITY ENUM
%token <String> ID

%type <PackageDeclaration> package_declaration
%type <Identifier> compound_identifier
%type <DflowFile> dflow_file
%type <Application> application_definition
%type <Application.Contents> application_contents

%start dflow_file

%%

package_declaration : PACKAGE compound_identifier ';' { $$ = new PackageDeclaration($2); } ;

compound_identifier : ID { $$ = new SimpleIdentifier($1); } ;

dflow_file : package_declaration application_definition { this.parsedFile = new DflowApplicationFile(this.file, $1, $2); } ;

application_definition : PUBLIC APPLICATION ID '{' application_contents '}' { $$ = new Application($3, $5); } ;

application_contents : /* empty */ { $$ = new Application.Contents(); } ;
