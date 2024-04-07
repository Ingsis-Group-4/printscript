package ast

import position.Position

class PrintLnNode(
    private val expression: ExpressionNode,
    private val start: Position,
    private val end: Position,
) : FunctionNode {
    override fun getExpression(): ExpressionNode = expression

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
