package ast

import position.Position

/**
 * Represents a unary operation node in the abstract syntax tree.
 *
 * @property operand The expression node representing the operand of the unary operation.
 * @property operatorNode The operator node representing the unary operator.
 * @property start The start position of the unary operation.
 * @property end The end position of the unary operation
 *
 * Example:
 * ```
 * -1
 * ```
 * In this example, `-1` is the unary operation node, `-` is the operator node and `1` is the operand.
 */

class UnaryOperation(
    private val operand: ExpressionNode,
    private val operatorNode: OperatorNode,
    private val start: Position,
    private val end: Position,
) : OperationNode {
    override fun getOperator(): OperatorNode = operatorNode

    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getOperand(): ExpressionNode = operand
}
