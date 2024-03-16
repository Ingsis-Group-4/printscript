package ast

class IdentifierNode(val variableName: String, val variableType: VariableType? = null) : ExpressionNode
