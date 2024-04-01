package formatter.rule

import ast.PrintLnNode
import ast.StatementNode
import formatter.utils.changeExpressionNodeLine
import position.Position

class LineBreakBeforePrintLnRule(private val amountOfSpaces: Int) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        if (index == 0) {
            return statementNode
        }
        when (statementNode) {
            is PrintLnNode -> {
                val previousStatement = statements[index - 1]
                val previousStatementLine = previousStatement.getStart().line
                val currentStatementLine = statementNode.getStart().line
                val lineDifferenceBetweenStatements = currentStatementLine - previousStatementLine
                if (isDifferenceAcceptable(lineDifferenceBetweenStatements)) {
                    return statementNode
                } else {
                    val newLinePosition = previousStatementLine + (amountOfSpaces + 1)
                    val newStartPosition = Position(newLinePosition, statementNode.getStart().column)
                    val newEndPosition = Position(newLinePosition, statementNode.getEnd().column)
                    val newExpressionNode = changeExpressionNodeLine(statementNode.expression, newLinePosition)
                    return PrintLnNode(newExpressionNode, newStartPosition, newEndPosition)
                }
            }
            else -> return statementNode
        }
    }

    private fun isDifferenceAcceptable(lineDifferenceBetweenStatements: Int) = lineDifferenceBetweenStatements <= amountOfSpaces + 1
}
