package ast

import position.Position

class NumberTypeNode(
    val start: Position,
    val end: Position,
) : VariableTypeNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
