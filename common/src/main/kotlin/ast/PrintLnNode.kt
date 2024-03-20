package ast

import position.Position

class PrintLnNode(
    val expression: ExpressionNode,
    private val start: Position,
    private val end: Position
) : FunctionStatementNode {
    override fun getStart(): Position = start
    override fun getEnd(): Position = end
}
