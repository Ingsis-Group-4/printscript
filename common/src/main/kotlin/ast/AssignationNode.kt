package ast

import position.Position

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
    private val start: Position,
    private val end: Position
) : VariableStatementNode{
    override fun getStart(): Position = start
    override fun getEnd(): Position = end
}
