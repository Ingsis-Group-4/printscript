package formatter

import ast.AST
import ast.FunctionStatementNode
import kotlin.reflect.KClass

class FunctionStatementNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val functionStatementNode = node as FunctionStatementNode
        val function = functionStatementNode.getFunctionNode()
        return buildString {
            append(
                formatterMap[function::class]?.format(function, rule, formatterMap)
                    ?: throw IllegalArgumentException("No formatter found for ${function::class}"),
            )
        }
    }
}
