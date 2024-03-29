package ast

import position.Position

class SubtractionOperatorNode(
    val start: Position,
    val end: Position,
) : OperatorNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
