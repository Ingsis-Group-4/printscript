package ast

import position.Position

class VariableTypeNode(
    val variableType: VariableType,
    private val start: Position,
    private val end: Position,
) : AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getVariableType(): String {
        return when (variableType) {
            VariableType.NUMBER -> "Number"
            VariableType.STRING -> "String"
        }
    }
}
