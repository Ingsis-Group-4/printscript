package formatter.rule

import ast.StatementNode

class LineBreakAfterSemicolonRule(private val hasLineBreak: Boolean) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        TODO("Not yet implemented")
    }
}
