package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.Writeable;
import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.statement.AbstractStatement;

public class StatementBlock implements Writeable {
	
	private final List<AbstractStatement> statements = new LinkedList<>();
	
	public StatementBlock addStatement(AbstractStatement statement) {
		this.statements.add(statement);
		return this;
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(statements);
	}

}
