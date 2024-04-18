package formatter.statement

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
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
                "println(${formatNextNode(formatterMap, printLnNode.getExpression(), rule)})",
            )
        }
    }
}
