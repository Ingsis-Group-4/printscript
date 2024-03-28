package ast

import position.Position

data class ProgramNode(
    val statements: List<StatementNode>,
    private val start: Position,
    private val end: Position,
) : AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
