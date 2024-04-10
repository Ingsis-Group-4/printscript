package formatter

import ast.AST
import kotlin.reflect.KClass

class ProgramNodeFormatter(private val formatterMap: Map<KClass<out AST>, Formatter>) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val programNode = node as ast.ProgramNode
        return programNode.statements.joinToString(";\n") { statement ->
            formatterMap[statement::class]?.format(statement, rule)
                ?: throw IllegalArgumentException("No formatter found for ${statement::class}")
        }
    }
}
