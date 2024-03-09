package org.example.ast

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
): VariableStatementNode