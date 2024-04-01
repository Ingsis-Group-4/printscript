package formatter.rule

import ast.AssignationNode
import ast.DivisionNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperatorNode
import ast.ProductNode
import ast.StatementNode
import ast.SubtractionNode
import ast.SumNode
import ast.VariableDeclarationNode
import formatter.utils.changeExpressionNodePositions
import formatter.utils.createNewAssignationNode
import formatter.utils.createNewDivisionNode
import formatter.utils.createNewProductNode
import formatter.utils.createNewSubtractionNode
import formatter.utils.createNewSumNode
import formatter.utils.createNewVariableDeclarationNode
import position.Position

class WhitespaceBeforeAndAfterOperatorRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        if (!hasSpace) {
            return statementNode
        }
        return when (statementNode) {
            is VariableDeclarationNode -> {
                if (statementNode.expression != null) {
                    createNewVariableDeclarationNode(
                        statementNode.identifier,
                        handleExpressionNode(statementNode.expression!!),
                        statementNode.keywordNode,
                        statementNode.colonNode,
                        statementNode.typeNode,
                        statementNode.equalsNode,
                        statementNode.getStart(),
                        statementNode.getEnd(),
                    )
                } else {
                    statementNode
                }
            }

            is AssignationNode -> {
                createNewAssignationNode(
                    statementNode.identifier,
                    handleExpressionNode(statementNode.expression),
                    statementNode.equalsNode,
                    statementNode.getStart(),
                    statementNode.getEnd(),
                )
            }

            else -> statementNode
        }
    }

    private fun handleExpressionNode(statementNode: ExpressionNode): ExpressionNode {
        when (statementNode) {
            is LiteralNode<*> -> {
                handleLiteralNode(statementNode)
            }
            is SumNode -> {
                handleSumNode(statementNode)
            }

            is SubtractionNode -> {
                handleSubtractionNode(statementNode)
            }
            is ProductNode -> {
                handleProductNode(statementNode)
            }
            is DivisionNode -> {
                handleDivisionNode(statementNode)
            }

            is IdentifierNode -> {
                handleIdentifierNode(statementNode)
            }

            else
            -> return statementNode
        }
        return statementNode
    }

    private fun handleSumNode(statementNode: SumNode): ExpressionNode {
        val leftNode = handleExpressionNode(statementNode.left)
        val rightNode = handleExpressionNode(statementNode.right)
        val operatorNode = statementNode.operatorNode
        val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
        val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
        if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
        val newRightNodeStartPosition = Position(rightNode.getStart().line, operatorNode.getEnd().column + 2)
        val newLeftNodeEndPosition = Position(leftNode.getEnd().line, operatorNode.getStart().column - 2)
        if (leftNodeHasSpace) {
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewSumNode(leftNode, newRightNode, operatorNode, statementNode)
        } else if (rightNodeHasSpace) {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            return createNewSumNode(newLeftNode, rightNode, operatorNode, statementNode)
        } else {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewSumNode(newLeftNode, newRightNode, operatorNode, statementNode)
        }
    }

    private fun areThereAlreadySpaces(
        leftNodeHasSpace: Boolean,
        rightNodeHasSpace: Boolean,
    ): Boolean {
        return leftNodeHasSpace && rightNodeHasSpace
    }

    private fun handleSubtractionNode(statementNode: SubtractionNode): ExpressionNode {
        val leftNode = handleExpressionNode(statementNode.left)
        val rightNode = handleExpressionNode(statementNode.right)
        val operatorNode = statementNode.operatorNode
        val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
        val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
        if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
        val newRightNodeStartPosition = Position(rightNode.getStart().line, operatorNode.getEnd().column + 2)
        val newLeftNodeEndPosition = Position(leftNode.getEnd().line, operatorNode.getStart().column - 2)
        if (leftNodeHasSpace) {
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewSubtractionNode(leftNode, newRightNode, operatorNode, statementNode)
        } else if (rightNodeHasSpace) {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            return createNewSubtractionNode(newLeftNode, rightNode, operatorNode, statementNode)
        } else {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewSubtractionNode(newLeftNode, newRightNode, operatorNode, statementNode)
        }
    }

    private fun handleProductNode(statementNode: ProductNode): ExpressionNode {
        val leftNode = handleExpressionNode(statementNode.left)
        val rightNode = handleExpressionNode(statementNode.right)
        val operatorNode = statementNode.operatorNode
        val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
        val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
        if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
        val newRightNodeStartPosition = Position(rightNode.getStart().line, operatorNode.getEnd().column + 2)
        val newLeftNodeEndPosition = Position(leftNode.getEnd().line, operatorNode.getStart().column - 2)
        if (leftNodeHasSpace) {
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewProductNode(leftNode, newRightNode, operatorNode, statementNode)
        } else if (rightNodeHasSpace) {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            return createNewProductNode(newLeftNode, rightNode, operatorNode, statementNode)
        } else {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewProductNode(newLeftNode, newRightNode, operatorNode, statementNode)
        }
    }

    private fun handleDivisionNode(statementNode: DivisionNode): ExpressionNode {
        val leftNode = handleExpressionNode(statementNode.left)
        val rightNode = handleExpressionNode(statementNode.right)
        val operatorNode = statementNode.operatorNode
        val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
        val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
        if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
        val newRightNodeStartPosition = Position(rightNode.getStart().line, operatorNode.getEnd().column + 2)
        val newLeftNodeEndPosition = Position(leftNode.getEnd().line, operatorNode.getStart().column - 2)
        if (leftNodeHasSpace) {
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewDivisionNode(leftNode, newRightNode, operatorNode, statementNode)
        } else if (rightNodeHasSpace) {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            return createNewDivisionNode(newLeftNode, rightNode, operatorNode, statementNode)
        } else {
            val newLeftNode = changeExpressionNodePositions(leftNode, leftNode.getStart(), newLeftNodeEndPosition)
            val newRightNode = changeExpressionNodePositions(rightNode, newRightNodeStartPosition, rightNode.getEnd())
            return createNewDivisionNode(newLeftNode, newRightNode, operatorNode, statementNode)
        }
    }

    private fun handleIdentifierNode(statementNode: IdentifierNode): IdentifierNode {
        return statementNode
    }

    private fun spaceBeforeRightNode(
        rightNode: ExpressionNode,
        operatorNode: OperatorNode,
    ): Boolean {
        val rightNodeHasSpace = rightNode.getStart().column == operatorNode.getEnd().column + 2
        return rightNodeHasSpace
    }

    private fun spaceAfterLeftNode(
        leftNode: ExpressionNode,
        operatorNode: OperatorNode,
    ): Boolean {
        val leftNodeHasSpace = leftNode.getEnd().column == operatorNode.getStart().column - 2
        return leftNodeHasSpace
    }

    private fun handleLiteralNode(statementNode: LiteralNode<*>): LiteralNode<*> {
        return statementNode
    }
}
