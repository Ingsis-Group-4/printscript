package ast

import position.Position

class BlockNode(
    private val start: Position,
    private val end: Position,
    private val statements: List<StatementNode>,
) : AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getStatements(): List<StatementNode> = statements
}
