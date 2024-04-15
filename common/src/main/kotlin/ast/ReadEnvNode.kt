package ast

import position.Position

class ReadEnvNode(
    private val start: Position,
    private val end: Position,
    private val expression: ExpressionNode,
) : FunctionNode {
    override fun getExpression(): ExpressionNode = expression

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
