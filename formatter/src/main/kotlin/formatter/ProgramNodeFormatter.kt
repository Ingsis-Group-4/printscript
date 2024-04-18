package formatter

import ast.AST
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class ProgramNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val programNode = node as ast.ProgramNode
        if (programNode.statements.isNotEmpty()) {
            return programNode.statements.joinToString(";\n") { statement ->
                formatNextNode(formatterMap, statement, rule)
            } + ';'
        }
        return ""
    }
}
