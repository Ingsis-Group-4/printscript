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
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        when (statementNode) {
            is FunctionStatementNode -> {
                return statementNode
            }
            is AssignationNode -> {
                val equalSignPosition = statementNode.equalsNode.getEnd()
                val expressionNodePosition = statementNode.expression.getStart()

                if (!hasSpace) {
                    if (equalSignPosition.column == expressionNodePosition.column - 1) {
                        return statementNode
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
                        return createNewAssignationNode(
                            statementNode.identifier,
                            newExpressionNode,
                            statementNode.equalsNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                } else {
                    if (equalSignPosition.column == expressionNodePosition.column - 2) {
                        return statementNode
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
                        return createNewAssignationNode(
                            statementNode.identifier,
                            newExpressionNode,
                            statementNode.equalsNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                }
            }
            is VariableDeclarationNode -> {
                if (statementNode.equalsNode == null || statementNode.expression == null) {
                    return statementNode
                }
                val equalSignPosition = statementNode.equalsNode!!.getEnd()
                val expressionNodePosition = statementNode.expression!!.getStart()
                if (!hasSpace) {
                    if (equalSignPosition.column == expressionNodePosition.column - 1) {
                        return statementNode
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
                        return createNewVariableDeclarationNode(
                            statementNode.identifier,
                            newExpressionNode,
                            statementNode.keywordNode,
                            statementNode.colonNode,
                            statementNode.typeNode,
                            statementNode.equalsNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                } else {
                    if (equalSignPosition.column == expressionNodePosition.column - 2) {
                        return statementNode
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
                        return createNewVariableDeclarationNode(
                            statementNode.identifier,
                            newExpressionNode,
                            statementNode.keywordNode,
                            statementNode.colonNode,
                            statementNode.typeNode,
                            statementNode.equalsNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                }
            }
        }
    }
}
// jdbjvb : String =   "a"
// 1234567890123456123456789
