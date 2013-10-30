package org.dflow.compiler.parser.ast;

import java.util.Arrays;
import java.util.Collections;


public abstract class Node {

	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] {});
	}
	
	public abstract static class Visitor {
		private final Iterable<? extends Node> nodes;
		
		public Visitor(Node node) {
			this(Collections.singleton(node));
		}
		
		public Visitor(Iterable<? extends Node> nodes) {
			this.nodes = nodes;
		}
		
		public enum Action {
			CONTINUE, STOP, SKIP;
		}
		
		public void visit() {
			for (Node n : nodes) {
				visit(n);
			}
		}
		
		private Action visit(Node n) {
			Action action = enter(n);
			try {
				if (action == Action.CONTINUE) {
					for (Node c : n.getChildren()) {
						if (visit(c) == Action.STOP) {
							return Action.STOP;
						}
					}
				}

				if (action == Action.STOP) {
					return Action.STOP;
				} else {
					return Action.CONTINUE;
				}
			} finally {
				leave(n);
			}
		}

		protected abstract Action enter(Node n);
		protected abstract void leave(Node n);
	}

}
