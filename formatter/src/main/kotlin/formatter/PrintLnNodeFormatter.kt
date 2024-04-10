package formatter

import ast.AST

class PrintLnNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val printLnNode = node as ast.PrintLnNode
        var resultString = ""
        val lineBreakAmount = rule.lineBreakBeforePrintLn.toInt()
        if (lineBreakAmount > 0) {
            for (i in 1..lineBreakAmount) {
                resultString += "\n"
            }
        }
        resultString += "println(${getExpression(printLnNode.getExpression(), rule)})"
        return resultString
    }
}
