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
        val whiteSpacesBeforePrint = " ".repeat(functionStatementNode.getStart().column - 1)
//      Ideally we should have the position of the colon, but we don't have it in the AST
        val whiteSpacesAfterPrint = " ".repeat(functionStatementNode.getEnd().column - functionStatementNode.expression.getEnd().column - 1)
        val expressionNode = ExpressionNodeStringifier().stringify(functionStatementNode.expression)
        val functionLength = 7 // "printLn(".length
        val whiteSpacesBeforeExpression =
            " ".repeat(
                (functionStatementNode.expression.getStart().column - (functionStatementNode.getStart().column + functionLength) - 1),
            )
        val whiteSpacesAfterExpression =
            " ".repeat(
                (functionStatementNode.getEnd().column - functionStatementNode.expression.getEnd().column - 1),
            )
        return whiteSpacesBeforePrint +
            "printLn(" +
            whiteSpacesBeforeExpression +
            expressionNode +
            "$whiteSpacesAfterExpression)" +
            whiteSpacesAfterPrint
    }
}
