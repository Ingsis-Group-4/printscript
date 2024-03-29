package formatter.rule

import ast.StatementNode

interface Rule {
    fun apply(statementNode: StatementNode):StatementNode
}