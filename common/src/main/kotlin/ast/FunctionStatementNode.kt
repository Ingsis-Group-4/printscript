package ast

import position.Position

/**
 * Represents a function statement node in the abstract syntax tree.
 *
 * @property start The start position of the statement.
 * @property end The end position of the statement.
 * @property functionNode The node that contain the function call.
 *
 * Example:
 * ```
 * printLn(1);
 * ```
 * In this example, `printLn(1);` is the function statement node and `printLn(1)` is the function node.
 */

class FunctionStatementNode(
    private val start: Position,
    private val end: Position,
    private val functionNode: FunctionNode,
) : StatementNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    fun getFunctionNode(): FunctionNode = functionNode
}
