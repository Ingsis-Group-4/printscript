package ast

import position.Position

class LetNode(
    private val start: Position,
    private val end: Position,
) : KeywordNode {
    private val keyword = "let"

    override fun getKeyword(): String {
        return keyword
    }

    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
