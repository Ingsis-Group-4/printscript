package ast

class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode? = null,
) : VariableStatementNode
