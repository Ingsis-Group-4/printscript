package ast

import position.Position

/**
 * Represents a PrintLn node in the abstract syntax tree.
 *
 * @property expression The expression to be printed.
 * @property start The start position of the PrintLn call.
 * @property end The end position of the PrintLn call.
 */

class PrintLnNode(
    private val expression: ExpressionNode,
    private val start: Position,
    private val end: Position,
) : FunctionNode {
    override fun getExpression(): ExpressionNode = expression

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
