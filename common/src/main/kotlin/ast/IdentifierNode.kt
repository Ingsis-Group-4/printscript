package ast

import position.Position

class IdentifierNode(
    val variableName: String,
    val variableType: VariableType? = null,
    private val start: Position,
    private val end: Position
) : ExpressionNode {
    override fun getStart(): Position = start
    override fun getEnd(): Position = end
}
