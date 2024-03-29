package ast

import position.Position

class EqualsNode(
    private val start: Position,
    private val end: Position,
) :
    AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
