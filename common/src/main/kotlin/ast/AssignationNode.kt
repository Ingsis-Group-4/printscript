package ast

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
) : VariableStatementNode
