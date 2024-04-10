package formatter

import ast.AST
import kotlin.reflect.KClass

class PrintLnNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val printLnNode = node as ast.PrintLnNode
        return buildString {
            repeat(rule.lineBreakBeforePrintLn) {
                append("\n")
            }
            append(
                "println(${formatterMap[printLnNode.getExpression()::class]?.format(printLnNode.getExpression(), rule, formatterMap)
                    ?: throw IllegalArgumentException("No formatter found for ${printLnNode.getExpression()::class}")})",
            )
        }
    }
}
