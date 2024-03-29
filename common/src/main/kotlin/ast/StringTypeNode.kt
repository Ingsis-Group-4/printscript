package ast

import position.Position

class StringTypeNode(
    val start: Position,
    val end: Position,
) : VariableTypeNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
