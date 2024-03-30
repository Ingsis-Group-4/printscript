package ast

import position.Position

class DivisionNode(
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operatorNode: OperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
