package org.example.ast

import org.example.interpreter.Visitor

class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode? = null,
) : VariableStatementNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
};
