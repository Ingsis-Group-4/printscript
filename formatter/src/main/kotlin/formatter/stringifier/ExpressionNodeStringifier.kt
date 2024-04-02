package formatter.stringifier

import ast.AST
import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperationNode
import ast.OperatorType

class ExpressionNodeStringifier : Stringifier {
    override fun stringify(node: AST): String {
        return when (val expressionNode = node as ExpressionNode) {
            is LiteralNode<*> -> handleLiteralNode(expressionNode)
            is OperationNode -> handleOperationNode(expressionNode)
            is IdentifierNode -> handleIdentifierNode(expressionNode)
        }
    }

    private fun handleIdentifierNode(identifierNode: IdentifierNode): String {
        return identifierNode.variableName
    }

    private fun handleOperationNode(operationNode: OperationNode): String {
        val whiteSpacesBeforeOperator = " ".repeat(operationNode.getOperator().getStart().column - operationNode.getLeft().getEnd().column)
        val whiteSpacesAfterOperator = " ".repeat(operationNode.getRight().getStart().column - operationNode.getOperator().getEnd().column)
        val leftSide = ExpressionNodeStringifier().stringify(operationNode.getLeft())
        val rightSide = ExpressionNodeStringifier().stringify(operationNode.getRight())
        val operator = operationNode.getOperator().getType()
        return when (operator) {
            OperatorType.SUM -> "$leftSide$whiteSpacesBeforeOperator+$whiteSpacesAfterOperator$rightSide"
            OperatorType.SUB -> "$leftSide$whiteSpacesBeforeOperator-$whiteSpacesAfterOperator$rightSide"
            OperatorType.MUL -> "$leftSide$whiteSpacesBeforeOperator*$whiteSpacesAfterOperator$rightSide"
            OperatorType.DIV -> "$leftSide$whiteSpacesBeforeOperator/$whiteSpacesAfterOperator$rightSide"
        }
    }

    private fun handleLiteralNode(literalNode: LiteralNode<*>): String {
        return when (literalNode.value) {
            is String -> "\"${literalNode.value}\""
            is Number -> literalNode.value.toString()
            else -> throw IllegalArgumentException("Unsupported literal type")
        }
    }
}
