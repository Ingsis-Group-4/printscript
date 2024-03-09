package org.example.ast

class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode?,
): VariableStatementNode;