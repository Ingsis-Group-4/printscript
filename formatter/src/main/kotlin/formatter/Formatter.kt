package formatter

import ast.AST
import ast.ProgramNode
import ast.StatementNode
import formatter.rule.Rule

class Formatter(private val node: ProgramNode, private val rules: List<Rule>) {
    fun format(): AST {
        var formattedStatements = mutableListOf<StatementNode>()
        var currentIndex = 0
        for (statement in node.statements) {
            for (rule in rules) {
                formattedStatements = rule.apply(currentIndex, node.statements).toMutableList()
            }
            currentIndex += 1
        }
        return ProgramNode(
            formattedStatements,
            formattedStatements[0].getStart(),
            formattedStatements[formattedStatements.size - 1].getEnd(),
        )
    }
}
