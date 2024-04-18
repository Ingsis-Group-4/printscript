package formatter.statement

import ast.AST
import ast.FunctionNode
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.DefaultWhitespacesBeforeFunction
import formatter.utils.formatNextNode
import formatter.utils.getClass
import kotlin.reflect.KClass

class FunctionCallFormatter(private val functionNameMap: Map<KClass<out FunctionNode>, String>) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val functionNode = node as FunctionNode
        return buildString {
            repeat(
                DefaultWhitespacesBeforeFunction.getWhitespacesAmount(functionNode, rule),
            ) {
                append("\n")
            }
            append(
                "${functionNameMap[getClass(functionNode)]}(${
                    formatNextNode(
                        formatterMap,
                        functionNode.getExpression(),
                        rule,
                    )
                })",
            )
        }
    }
}
