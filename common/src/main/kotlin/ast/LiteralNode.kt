package ast

import position.Position

class LiteralNode<T>(
    val value: T,
    private val start: Position,
    private val end: Position,
) : ExpressionNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
