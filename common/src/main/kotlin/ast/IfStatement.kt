package ast

import position.Position

class IfStatement(
    private val start: Position,
    private val end: Position,
    private val condition: ExpressionNode,
    private val thenBlock: BlockNode,
    private val elseBlock: BlockNode?,
) : StatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getCondition(): ExpressionNode = condition

    fun getThenBlock(): BlockNode = thenBlock

    fun getElseBlock(): BlockNode? = elseBlock
}
