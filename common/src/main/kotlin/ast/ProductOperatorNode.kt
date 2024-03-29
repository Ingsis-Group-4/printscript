package ast

import position.Position

class ProductOperatorNode(
    val start: Position,
    val end: Position,
) : OperatorNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
