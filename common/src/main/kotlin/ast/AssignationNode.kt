package ast

import position.Position

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
    val equalsNode: EqualsNode,
    private val start: Position,
    private val end: Position,
) : VariableStatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
