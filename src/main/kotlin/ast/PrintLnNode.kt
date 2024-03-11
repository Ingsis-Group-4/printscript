package org.example.ast

import org.example.interpreter.Visitor

class PrintLnNode(val expression: ExpressionNode) : FunctionStatementNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
