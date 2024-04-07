package ast

import position.Position

class FunctionStatementNode(
    private val start: Position,
    private val end: Position,
    private val functionNode: FunctionNode,
) : StatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getFunctionNode(): FunctionNode = functionNode
}
