package formatter

import ast.AST
import ast.UnaryOperation
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class UnaryOperationNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val unaryOperationNode = node as UnaryOperation
        return buildString {
            append(unaryOperationNode.getOperator())
            append(if (rule.hasWhiteSpaceBeforeAndAfterOperation) " " else "")
            append(
                formatNextNode(
                    formatterMap,
                    unaryOperationNode.getOperand(),
                    rule,
                ),
            )
        }
    }
}
