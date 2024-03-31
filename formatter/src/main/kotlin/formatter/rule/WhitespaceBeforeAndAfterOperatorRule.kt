package formatter.rule

import ast.StatementNode

class WhitespaceBeforeAndAfterOperatorRule(hasSpace: Boolean) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        TODO("Not yet implemented")
    }
}
