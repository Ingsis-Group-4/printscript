package formatter.rule

import ast.AssignationNode
import ast.FunctionStatementNode
import ast.StatementNode
import ast.VariableDeclarationNode
import formatter.utils.changeExpressionNodePositions
import formatter.utils.createNewAssignationNode
import formatter.utils.createNewVariableDeclarationNode
import position.Position

class SpaceAfterEqualSignRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        when (val statementNode = statements[currentStatementIndex]) {
            is FunctionStatementNode -> {
                return statements
            }
            is AssignationNode -> {
                val equalSignPosition = statementNode.equalsNode.getEnd()
                val expressionNodePosition = statementNode.expression.getStart()

                if (!hasSpace) {
                    if (equalSignPosition.column == expressionNodePosition.column - 1) {
                        return statements
                    } else {
                        val differenceBetweenPositions = expressionNodePosition.column - equalSignPosition.column
                        val newExpressionNodeStartPosition =
                            Position(expressionNodePosition.line, expressionNodePosition.column - (differenceBetweenPositions - 1))
                        val newExpressionNodeEndPosition =
                            Position(
                                expressionNodePosition.line,
                                statementNode.expression.getEnd().column - (differenceBetweenPositions - 1),
                            )
                        val newExpressionNode =
                            changeExpressionNodePositions(
                                statementNode.expression,
                                newExpressionNodeStartPosition,
                                newExpressionNodeEndPosition,
                            )
                        val auxStatementList = statements.toMutableList()
                        val newAssignationNode =
                            createNewAssignationNode(
                                statementNode.identifier,
                                newExpressionNode,
                                statementNode.equalsNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newAssignationNode
                        return auxStatementList
                    }
                } else {
                    if (equalSignPosition.column == expressionNodePosition.column - 2) {
                        return statements
                    } else {
                        val differenceBetweenPositions = expressionNodePosition.column - equalSignPosition.column
                        val newExpressionNodeStartPosition =
                            Position(expressionNodePosition.line, expressionNodePosition.column - (differenceBetweenPositions - 2))
                        val newExpressionNodeEndPosition =
                            Position(
                                expressionNodePosition.line,
                                statementNode.expression.getEnd().column - (differenceBetweenPositions - 2),
                            )
                        val newExpressionNode =
                            changeExpressionNodePositions(
                                statementNode.expression,
                                newExpressionNodeStartPosition,
                                newExpressionNodeEndPosition,
                            )
                        val auxStatementList = statements.toMutableList()
                        val newAssignationNode =
                            createNewAssignationNode(
                                statementNode.identifier,
                                newExpressionNode,
                                statementNode.equalsNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newAssignationNode
                        return auxStatementList
                    }
                }
            }
            is VariableDeclarationNode -> {
                if (statementNode.equalsNode == null || statementNode.expression == null) {
                    return statements
                }
                val equalSignPosition = statementNode.equalsNode!!.getEnd()
                val expressionNodePosition = statementNode.expression!!.getStart()
                if (!hasSpace) {
                    if (equalSignPosition.column == expressionNodePosition.column - 1) {
                        return statements
                    } else {
                        val differenceBetweenPositions = expressionNodePosition.column - equalSignPosition.column
                        val newExpressionNodeStartPosition =
                            Position(expressionNodePosition.line, expressionNodePosition.column - (differenceBetweenPositions - 1))
                        val newExpressionNodeEndPosition =
                            Position(
                                expressionNodePosition.line,
                                statementNode.expression!!.getEnd().column - (differenceBetweenPositions - 1),
                            )
                        val newExpressionNode =
                            changeExpressionNodePositions(
                                statementNode.expression!!,
                                newExpressionNodeStartPosition,
                                newExpressionNodeEndPosition,
                            )
                        val auxStatementList = statements.toMutableList()
                        val newVariableDeclarationNode =
                            createNewVariableDeclarationNode(
                                statementNode.identifier,
                                newExpressionNode,
                                statementNode.keywordNode,
                                statementNode.colonNode,
                                statementNode.typeNode,
                                statementNode.equalsNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                        return auxStatementList
                    }
                } else {
                    if (equalSignPosition.column == expressionNodePosition.column - 2) {
                        return statements
                    } else {
                        val differenceBetweenPositions = expressionNodePosition.column - equalSignPosition.column
                        val newExpressionNodeStartPosition =
                            Position(expressionNodePosition.line, expressionNodePosition.column - (differenceBetweenPositions - 2))
                        val newExpressionNodeEndPosition =
                            Position(
                                expressionNodePosition.line,
                                statementNode.expression!!.getEnd().column - (differenceBetweenPositions - 2),
                            )
                        val newExpressionNode =
                            changeExpressionNodePositions(
                                statementNode.expression!!,
                                newExpressionNodeStartPosition,
                                newExpressionNodeEndPosition,
                            )
                        val auxStatementList = statements.toMutableList()
                        val newVariableDeclarationNode =
                            createNewVariableDeclarationNode(
                                statementNode.identifier,
                                newExpressionNode,
                                statementNode.keywordNode,
                                statementNode.colonNode,
                                statementNode.typeNode,
                                statementNode.equalsNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                        return auxStatementList
                    }
                }
            }
        }
    }
}
