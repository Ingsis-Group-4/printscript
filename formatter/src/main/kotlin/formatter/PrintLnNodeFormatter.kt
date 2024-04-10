package formatter

import ast.AST

class PrintLnNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val printLnNode = node as ast.PrintLnNode
        return buildString {
            repeat(rule.lineBreakBeforePrintLn) {
                append("\n")
            }
            append("println(${getExpression(printLnNode.getExpression(), rule)})")
        }
    }
}
