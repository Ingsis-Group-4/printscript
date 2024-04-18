package formatter.utils

import ast.FunctionNode
import formatter.rule.FormattingRule

interface WhitespacesBeforeFunction {
    fun getWhitespacesAmount(
        functionNode: FunctionNode,
        formattingRule: FormattingRule,
    ): Int
}

object DefaultWhitespacesBeforeFunction : WhitespacesBeforeFunction {
    override fun getWhitespacesAmount(
        functionNode: FunctionNode,
        formattingRule: FormattingRule,
    ): Int {
        return when (functionNode) {
            is ast.ReadInputNode -> 0
            is ast.ReadEnvNode -> 0
            is ast.PrintLnNode -> formattingRule.lineBreakBeforePrintLn

            else -> 0
        }
    }
}
