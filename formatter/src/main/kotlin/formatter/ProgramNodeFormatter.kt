package formatter

import ast.AST
import kotlin.reflect.KClass

class ProgramNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val programNode = node as ast.ProgramNode
        return programNode.statements.joinToString(";\n") { statement ->
            formatterMap[statement::class]?.format(statement, rule, formatterMap)
                ?: throw IllegalArgumentException("No formatter found for ${statement::class}")
        }
    }
}
