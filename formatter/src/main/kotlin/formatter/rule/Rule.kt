package formatter.rule

import ast.StatementNode

interface Rule {
    fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode
}
