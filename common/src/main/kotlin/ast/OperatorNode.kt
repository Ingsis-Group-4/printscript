package ast

import position.Position

enum class OperatorType {
    SUM,
    SUB,
    MUL,
    DIV,
}

class OperatorNode(
    private val start: Position,
    private val end: Position,
    private val type: OperatorType,
) : AST {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getType(): OperatorType = type
}
