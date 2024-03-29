package ast

import position.Position

class SubtractionNode(
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operatorNode: SubtractionOperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
