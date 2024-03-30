package ast

import position.Position

class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode? = null,
    val keywordNode: KeywordNode,
    val colonNode: ColonNode,
    val equalsNode: EqualsNode?,
    private val start: Position,
    private val end: Position,
) : VariableStatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
