package ast

/**
 * Represents a function node in the abstract syntax tree.
 *
 * It's position starts at the beginning of the function call and ends at the end of it.
 */

sealed interface FunctionNode : ExpressionNode {
    fun getExpression(): ExpressionNode
}
