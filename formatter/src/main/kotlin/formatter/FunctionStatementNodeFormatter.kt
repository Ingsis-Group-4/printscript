package formatter

import ast.AST
import ast.FunctionStatementNode
import formatter.utils.formatNextNode
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
                formatNextNode(
                    formatterMap,
                    function,
                    rule,
                ),
            )
        }
    }
}
