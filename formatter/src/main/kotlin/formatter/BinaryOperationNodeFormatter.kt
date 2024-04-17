package formatter

import ast.AST
import ast.BinaryOperation
import ast.OperatorNode
import ast.OperatorType
import formatter.utils.OperatorFormatter
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class BinaryOperationNodeFormatter(private val operatorFormatter: OperatorFormatter) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val binaryOperationNode = node as BinaryOperation
        val space = if (rule.hasWhiteSpaceBeforeAndAfterOperation) " " else ""
        val formattedLeft = formatNextNode(formatterMap, binaryOperationNode.getLeft(), rule)
        val formattedRight = formatNextNode(formatterMap, binaryOperationNode.getRight(), rule)
        val formattedOperator = operatorFormatter.format(getOperatorType(binaryOperationNode.getOperator()))
        return buildFormattedOutput(formattedLeft, formattedOperator, formattedRight, space)
    }

    private fun buildFormattedOutput(
        left: String,
        operator: String,
        right: String,
        space: String,
    ) = "$left$space$operator$space$right"

    private fun getOperatorType(operator: OperatorNode): OperatorType {
        return operator.getType()
    }
}
