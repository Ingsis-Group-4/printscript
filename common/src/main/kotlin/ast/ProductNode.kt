package ast

import position.Position

class ProductNode(
    val left: ExpressionNode,
    val right: ExpressionNode,
    private val operatorNode: OperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getOperator(): OperatorNode = operatorNode

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
