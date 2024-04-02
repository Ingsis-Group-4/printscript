package formatter.rule

import ast.PrintLnNode
import ast.StatementNode
import formatter.utils.changeExpressionNodeLine
import position.Position

class LineBreakBeforePrintLnRule(private val amountOfSpaces: Int) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        if (currentStatementIndex == 0) {
            return statements
        }
        when (val statementNode = statements[currentStatementIndex]) {
            is PrintLnNode -> {
                val previousStatement = statements[currentStatementIndex - 1]
                val previousStatementLine = previousStatement.getStart().line
                val currentStatementLine = statementNode.getStart().line
                val lineDifferenceBetweenStatements = currentStatementLine - previousStatementLine
                if (isDifferenceAcceptable(lineDifferenceBetweenStatements)) {
                    return statements
                } else {
                    val auxStatementList = statements.toMutableList()
                    val newLinePosition = previousStatementLine + (amountOfSpaces + 1)
                    val newStartPosition = Position(newLinePosition, statementNode.getStart().column)
                    val newEndPosition = Position(newLinePosition, statementNode.getEnd().column)
                    val newExpressionNode = changeExpressionNodeLine(statementNode.expression, newLinePosition)
                    val newPrintLnNode = PrintLnNode(newExpressionNode, newStartPosition, newEndPosition)
                    auxStatementList[currentStatementIndex] = newPrintLnNode
                    return auxStatementList
                }
            }
            else -> return statements
        }
    }

    private fun isDifferenceAcceptable(lineDifferenceBetweenStatements: Int) = lineDifferenceBetweenStatements <= amountOfSpaces + 1
}
