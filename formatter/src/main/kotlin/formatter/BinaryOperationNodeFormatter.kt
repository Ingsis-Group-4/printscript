package formatter

import ast.AST
import ast.BinaryOperation
import kotlin.reflect.KClass

class BinaryOperationNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val binaryOperationNode = node as BinaryOperation
        val space = if (rule.hasWhiteSpaceBeforeAndAfterOperation) " " else ""
        return "${formatNode(
            binaryOperationNode.getLeft(),
            rule,
            formatterMap,
        )}$space${getOperator(binaryOperationNode)}$space${formatNode(binaryOperationNode.getRight(), rule, formatterMap)}"
    }

    private fun getOperator(binaryOperationNode: BinaryOperation): String {
        return when (binaryOperationNode.getOperator().getType()) {
            ast.OperatorType.SUM -> "+"
            ast.OperatorType.SUBTRACT -> "-"
            ast.OperatorType.MULTIPLICATION -> "*"
            ast.OperatorType.DIVISION -> "/"
            ast.OperatorType.NEGATION -> "!"
        }
    }

    private fun formatNode(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        return formatterMap[node::class]?.format(node, rule, formatterMap)
            ?: throw IllegalArgumentException("No formatter found for ${node::class}")
    }
}
