%{
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.IOException;

import org.dflow.compiler.parser.exceptions.*;
import org.dflow.compiler.model.datamodel.*;
%}

%token PACKAGE PUBLIC ENTITY
%token WORD
%start entity_definition

%%

entity_definition : package_declaration entity_signature '{' entity_contents '}' ;

package_declaration : PACKAGE package_name ';' { this.packageName = $2.sval; } ;

package_name : WORD
             | package_name '.' WORD { $$.sval = $1.sval + '.' + $3.sval; } ;

entity_signature : PUBLIC ENTITY WORD { this.entity = new Entity(packageName, $3.sval); } ;

entity_contents : /* empty */
                | attribute_definition entity_contents ;

attribute_definition : attribute_type WORD ';' { this.entity.addAttribute(new Attribute($2.sval, $1.sval)); } ;

attribute_type : WORD
               | WORD '[' ']' { $$.sval = $1.sval + '[' + ']'; } ;

%%

private final StreamTokenizer st;
private String packageName;
private Entity entity;

public EntityParser(Reader reader) {
	this.st = new StreamTokenizer(reader);
}

public Entity getEntity() {
	return entity;
}

public Entity parse() throws IOException, ParseException {
	yyparse();
	return entity;
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
			yylval = new EntityParserVal(st.sval);
			return WORD;
		}
	} else {
		return token;
	}
}

private void yyerror(String s) throws ParseException {
	throw new ParseException(st.lineno());
}
