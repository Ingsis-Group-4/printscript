package org.example.ast

import org.example.interpreter.Visitor

class IdentifierNode(val variableName: String, val variableType: VariableType?) : ExpressionNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
