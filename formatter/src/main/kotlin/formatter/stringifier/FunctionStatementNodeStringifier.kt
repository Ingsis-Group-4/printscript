package formatter.stringifier

import ast.AST
import ast.FunctionStatementNode
import ast.PrintLnNode

class FunctionStatementNodeStringifier : Stringifier {
    override fun stringify(node: AST): String {
        return when (val functionStatementNode = node as FunctionStatementNode) {
            is PrintLnNode -> handlePrintLnNode(functionStatementNode)
        }
    }

    private fun handlePrintLnNode(functionStatementNode: PrintLnNode): String {
        val whiteSpacesBeforePrint = " ".repeat(functionStatementNode.getStart().column)
        val whiteSpacesAfterPrint = " ".repeat(functionStatementNode.getEnd().column - functionStatementNode.expression.getEnd().column)
        val expressionNode = ExpressionNodeStringifier().stringify(functionStatementNode.expression)
        val functionLength = functionStatementNode.expression.getEnd().column - functionStatementNode.expression.getStart().column
        val whiteSpacesBeforeExpression =
            " ".repeat(
                (functionStatementNode.expression.getStart().column - (functionStatementNode.getStart().column + functionLength)),
            )
        val whiteSpacesAfterExpression =
            " ".repeat(
                (functionStatementNode.getEnd().column - functionStatementNode.expression.getEnd().column),
            )
        return whiteSpacesBeforePrint +
            "printLn(" +
            whiteSpacesBeforeExpression +
            expressionNode +
            "$whiteSpacesAfterExpression)" +
            whiteSpacesAfterPrint
    }
}
