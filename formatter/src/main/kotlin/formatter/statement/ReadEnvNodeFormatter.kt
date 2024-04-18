package formatter.statement

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class ReadEnvNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val readEnvNode = node as ast.ReadEnvNode
        return buildString {
//            repeat(rule.lineBreakBeforePrintLn) {
//                append("\n")
//            }
            append(
                "readEnv(${formatNextNode(formatterMap, readEnvNode.getExpression(), rule)})",
            )
        }
    }
}
