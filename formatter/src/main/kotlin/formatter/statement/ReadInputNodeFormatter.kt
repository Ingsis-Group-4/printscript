package formatter.statement

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class ReadInputNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val readInputNode = node as ast.ReadInputNode
        return buildString {
//            repeat(rule.lineBreakBeforePrintLn) {
//                append("\n")
//            }
            append(
                "readInput(${formatNextNode(formatterMap, readInputNode.getExpression(), rule)})",
            )
        }
    }
}
