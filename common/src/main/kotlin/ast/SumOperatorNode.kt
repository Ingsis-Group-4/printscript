package ast

import position.Position

class SumOperatorNode(
    val start: Position,
    val end: Position,
) : OperatorNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
