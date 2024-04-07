package formatter

import ast.AST
import ast.ProgramNode
import formatter.rule.Rule

class AstRuleProcessor(private val rules: List<Rule>) {
    fun format(node: ProgramNode): AST {
        val formattedStatements = node.statements.toMutableList()
        var currentIndex = 0
        for (statement in node.statements) {
            for (rule in rules) {
                val newStatements = rule.apply(currentIndex, formattedStatements).toMutableList()
                formattedStatements[currentIndex] = newStatements[currentIndex]
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
