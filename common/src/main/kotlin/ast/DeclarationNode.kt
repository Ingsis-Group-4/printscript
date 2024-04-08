package ast

import position.Position

/**
 * Represents a declaration node in the abstract syntax tree.
 *
 * @property identifier The identifier node of the variable being declared.
 * @property expression The expression node representing the value being assigned to the variable. This is optional.
 * @property keywordNode The keyword node representing the keyword used in the declaration (e.g., 'let').
 * @property colonNode The colon node representing the colon used in the declaration.
 * @property typeNode The type node representing the type of the variable being declared.
 * @property equalsNode The equals node representing the assignment operation. This is optional.
 * @property start The start position of the declaration.
 * @property end The end position of the declaration.
 *
 * Example:
 * ```
 * let a: Number = 1;
 * ```
 *
 * In this example, `let` is the keyword node, `a` is the identifier, `Number` is the type node, and `1` is the expression.
 */

class DeclarationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode? = null,
    val keywordNode: KeywordNode,
    val colonNode: ColonNode,
    val typeNode: VariableTypeNode,
    val equalsNode: EqualsNode?,
    private val start: Position,
    private val end: Position,
) : VariableStatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
