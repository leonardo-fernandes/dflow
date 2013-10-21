%{
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.IOException;

import org.dflow.compiler.parser.exceptions.*;
import org.dflow.compiler.model.*;
%}

%token PACKAGE PUBLIC APPLICATION
%token WORD
%start application_definition

%%

application_definition : package_declaration application_signature '{' application_contents '}' ;

package_declaration : PACKAGE package_name ';' ;

package_name : WORD
             | package_name '.' WORD ;

application_signature : PUBLIC APPLICATION WORD { this.application = new Application($package, $3.sval); } ;

application_contents : /* empty */ ;

%%

private final StreamTokenizer st;
private final String $package;
private Application application;

public ApplicationParser(String $package, Reader reader) {
	this.$package = $package;
	this.st = new StreamTokenizer(reader);
}

public Application getApplication() {
	return application;
}

public void parse() throws IOException, ParseException {
	yyparse();
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
		case "application":
			return APPLICATION;
		default:
			yylval = new ApplicationParserVal(st.sval);
			return WORD;
		}
	} else {
		return token;
	}
}

private void yyerror(String s) throws ParseException {
	throw new ParseException(st.lineno());
}
