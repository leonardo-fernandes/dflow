package org.dflow.compiler.parser;
import java.io.File;
import static org.dflow.compiler.parser.ApplicationParser.PACKAGE;
import static org.dflow.compiler.parser.ApplicationParser.IMPORT;
import static org.dflow.compiler.parser.ApplicationParser.PUBLIC;
import static org.dflow.compiler.parser.ApplicationParser.PRIVATE;
import static org.dflow.compiler.parser.ApplicationParser.PROTECTED;
import static org.dflow.compiler.parser.ApplicationParser.APPLICATION;
import static org.dflow.compiler.parser.ApplicationParser.ENTITY;
import static org.dflow.compiler.parser.ApplicationParser.ENUM;
import static org.dflow.compiler.parser.ApplicationParser.ID;

import static org.dflow.compiler.parser.ApplicationParser.EOF;

%%

%public
%final
%class DflowLexer
%implements ApplicationParser.Lexer, DataModelParser.Lexer
%ctorarg File file

%int
%eofclose

%unicode
%line
%column

%{
	private final File file;

	@Override
	public Position getStartPos() {
		return new Position(file, yyline, yycolumn);
	}

	@Override
	public Position getEndPos() {
		return new Position(file, yyline, yycolumn + yylength());
	}

	@Override
	public Object getLVal() {
		return yytext();
	}

	@Override
	public void yyerror(org.dflow.compiler.parser.ApplicationParser.Location loc, String s) {
	}

	@Override
	public void yyerror(org.dflow.compiler.parser.DataModelParser.Location loc, String s) {
	}
%}
%init{
	this.file = file;
%init}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t]

Identifier = [:jletter:][:jletterdigit:]*
CompoundIdentifier = {Identifier} ( "." {Identifier} )*

%%

<YYINITIAL> {
	"package"     { return PACKAGE; }
	"import"      { return IMPORT; }
	"public"      { return PUBLIC; }
	"private"     { return PRIVATE; }
	"protected"   { return PROTECTED; }
	"application" { return APPLICATION; }
	"entity"      { return ENTITY; }
	"enum"        { return ENUM; }
	{CompoundIdentifier}  { return ID; }

	{WhiteSpace}  { /* ignore */ }
	<<EOF>>       { return EOF; }

	.|\n          { return yycharat(0); }
}

<<EOF>> { return EOF; }
