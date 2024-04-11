package ast

import position.Position

class ReadInputNode(
    private val start: Position,
    private val end: Position,
    private val expression: ExpressionNode,
) : FunctionNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    override fun getExpression(): ExpressionNode = expression
}
