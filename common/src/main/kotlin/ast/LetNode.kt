package ast

import position.Position

class LetNode(
    private val start: Position,
    private val end: Position,
) : KeywordNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
