package org.example.ast

import org.example.interpreter.Visitor

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
) : VariableStatementNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
