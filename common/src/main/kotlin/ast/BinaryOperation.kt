package ast

import position.Position

/**
 * Represents a binary operation node in the abstract syntax tree.
 *
 * @property left The left operand of the binary operation.
 * @property right The right operand of the binary operation.
 * @property operatorNode The operator of the binary operation.
 * @property start The start position of the binary operation.
 * @property end The end position of the binary operation.
 */

class BinaryOperation(
    private val left: ExpressionNode,
    private val right: ExpressionNode,
    private val operatorNode: OperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getOperator(): OperatorNode = operatorNode

    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getLeft(): ExpressionNode = left

    fun getRight(): ExpressionNode = right
}
