package org.dflow.compiler.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class DataModelLexer implements DataModelParser.Lexer {
	
	private final StreamTokenizer st;
	
	public DataModelLexer(Reader reader) {
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
				return DataModelParser.PACKAGE;
			case "import":
				return DataModelParser.IMPORT;
			case "public":
				return DataModelParser.PUBLIC;
			case "application":
				return DataModelParser.APPLICATION;
			case "entity":
				return DataModelParser.ENTITY;
			case "enum":
				return DataModelParser.ENUM;
			default:
				return DataModelParser.ID;
			}
		} else {
			return token;
		}
	}

	@Override
	public void yyerror(DataModelParser.Location loc, String s) {
		System.err.println(s);
	}

}
