package formatter

import ast.AST
import ast.ProgramNode
import ast.StatementNode
import formatter.rule.Rule

class Formatter(private val node: ProgramNode, private val rules: List<Rule>) {
    fun format(): AST {
        val formattedStatements = mutableListOf<StatementNode>()
        var index = 0
        for (statement in node.statements) {
            var formattedStatement = statement
            for (rule in rules) {
                formattedStatement = rule.apply(formattedStatement, index, node.statements)
            }
            formattedStatements.add(formattedStatement)
            index += 1
        }
        return ProgramNode(
            formattedStatements,
            formattedStatements[0].getStart(),
            formattedStatements[formattedStatements.size - 1].getEnd(),
        )
    }
}
