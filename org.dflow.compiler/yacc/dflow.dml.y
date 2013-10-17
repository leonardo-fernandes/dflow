%{
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.IOException;

import org.dflow.compiler.model.*;
import org.dflow.compiler.model.entities.*;
%}

%token PACKAGE PUBLIC ENTITY
%token WORD
%start entity_definition

%%

entity_definition : package_declaration entity_signature '{' entity_contents '}' ;

package_declaration : PACKAGE package_name ';' ;

package_name : WORD
             | package_name '.' WORD ;

entity_signature : PUBLIC ENTITY WORD { this.entity = new Entity($3.sval); } ;

entity_contents : /* empty */
                | attribute_definition entity_contents ;

attribute_definition : attribute_type WORD ';' { this.entity.addAttribute(new Attribute($2.sval, $1.sval)); } ;

attribute_type : WORD ;

%%

private final StreamTokenizer st;
private Entity entity;

public DataModelParser(Reader reader) {
	this.st = new StreamTokenizer(reader);
}

private int yylex() throws IOException {
	int token = st.nextToken();

	if (token == StreamTokenizer.TT_EOF) {
		return 0;
	} else if (token == StreamTokenizer.TT_WORD) {
		switch (st.sval) {
		case "package":
			return PACKAGE;
		case "public":
			return PUBLIC;
		case "entity":
			return ENTITY;
		default:
			yylval = new DataModelParserVal(st.sval);
			return WORD;
		}
	} else {
		return token;
	}
}

private void yyerror(String s) throws ParseException {
	throw new ParseException(st.lineno());
}
