package ast

import position.Position

/**
 * Represents an assignation node in the abstract syntax tree.
 *
 * @property identifier The identifier node of the variable being assigned.
 * @property expression The expression node representing the value being assigned to the variable.
 * @property equalsNode The equals node representing the assignment operation.
 * @property start The start position of the assignation.
 * @property end The end position of the assignation.
 *
 * Example:
 * ```
 * a = 1;
 * ```
 * In this example, `a` is the identifier, `1` is the expression, and `=` is the equals node.
 */

class AssignationNode(
    val identifier: IdentifierNode,
    val expression: ExpressionNode,
    val equalsNode: EqualsNode,
    private val start: Position,
    private val end: Position,
) : VariableStatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end
}
