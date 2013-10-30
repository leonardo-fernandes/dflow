package org.dflow.compiler.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class ApplicationLexer implements ApplicationParser.Lexer {
	
	private final StreamTokenizer st;
	
	public ApplicationLexer(Reader reader) {
		this.st = new StreamTokenizer(reader);
	}

	@Override
	public Position getStartPos() {
		return new Position(st.lineno());
	}

	@Override
	public Position getEndPos() {
		return new Position(st.lineno());
	}

	@Override
	public Object getLVal() {
		return st.sval;
	}

	@Override
	public int yylex() throws IOException {
		int token = st.nextToken();

		if (token == StreamTokenizer.TT_EOF) {
			return 0;
		} else if (token == StreamTokenizer.TT_WORD) {
			switch (st.sval) {
			case "package":
				return ApplicationParser.PACKAGE;
			case "import":
				return ApplicationParser.IMPORT;
			case "public":
				return ApplicationParser.PUBLIC;
			case "application":
				return ApplicationParser.APPLICATION;
			case "entity":
				return ApplicationParser.ENTITY;
			case "enum":
				return ApplicationParser.ENUM;
			default:
				return ApplicationParser.ID;
			}
		} else {
			return token;
		}
	}

	@Override
	public void yyerror(ApplicationParser.Location loc, String s) {
		System.err.println(s);
	}

}
