package formatter.rule

import ast.StatementNode

interface Rule {
    fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode>
}
