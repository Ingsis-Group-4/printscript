package ast

import position.Position

class VariableTypeNode(
    val variableType: VariableType,
    private val start: Position,
    private val end: Position,
) : AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
