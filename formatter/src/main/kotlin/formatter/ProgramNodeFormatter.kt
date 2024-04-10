package formatter

import ast.AST
import kotlin.reflect.KClass

class ProgramNodeFormatter(private val formatterMap: Map<KClass<out AST>, Formatter>) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val programNode = node as ast.ProgramNode
        val stringStatements = mutableListOf<String>()
        for (statements in programNode.statements) {
            val formatter = formatterMap[statements::class] ?: throw IllegalArgumentException("No formatter found for ${statements::class}")
            stringStatements.add(formatter.format(statements, rule))
        }
        return stringStatements.joinToString(";\n")
    }
}
