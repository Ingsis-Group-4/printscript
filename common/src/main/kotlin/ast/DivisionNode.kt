package ast

import position.Position

class DivisionNode(
    private val left: ExpressionNode,
    private val right: ExpressionNode,
    private val operatorNode: OperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getOperator(): OperatorNode {
        return operatorNode
    }

    override fun getLeft(): ExpressionNode {
        return left
    }

    override fun getRight(): ExpressionNode {
        return right
    }

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
