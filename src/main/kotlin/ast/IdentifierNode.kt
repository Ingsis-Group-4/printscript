package org.example.ast

import org.example.common.visitor.Visitor

class IdentifierNode(val variableName: String, val variableType: VariableType? = null) : ExpressionNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
