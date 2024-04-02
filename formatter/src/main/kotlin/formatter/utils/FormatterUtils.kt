package formatter.utils

import ast.AssignationNode
import ast.ColonNode
import ast.DivisionNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.KeywordNode
import ast.LiteralNode
import ast.OperatorNode
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import position.Position

fun changeExpressionNodePositions(
    expressionNode: ExpressionNode,
    newExpressionNodeStartPosition: Position,
    newExpressionNodeEndPosition: Position,
): ExpressionNode {
    when (expressionNode) {
        is LiteralNode<*> -> {
            return LiteralNode((expressionNode).value, newExpressionNodeStartPosition, newExpressionNodeEndPosition)
        }

        is IdentifierNode -> {
            return IdentifierNode(
                expressionNode.variableName,
                expressionNode.variableType,
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is SumNode -> {
            return SumNode(
                expressionNode.left,
                expressionNode.right,
                expressionNode.operatorNode,
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is ProductNode -> {
            return ProductNode(
                expressionNode.left,
                expressionNode.right,
                expressionNode.operatorNode,
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is DivisionNode -> {
            return DivisionNode(
                expressionNode.left,
                expressionNode.right,
                expressionNode.operatorNode,
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is SubtractionNode -> {
            return SubtractionNode(
                expressionNode.left,
                expressionNode.right,
                expressionNode.operatorNode,
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        else -> {
            return expressionNode
        }
    }
}

fun createNewAssignationNode(
    identifierNode: IdentifierNode,
    expressionNode: ExpressionNode,
    equalsNode: EqualsNode,
    start: Position,
    end: Position,
): AssignationNode {
    return AssignationNode(identifierNode, expressionNode, equalsNode, start, end)
}

fun createNewVariableDeclarationNode(
    identifierNode: IdentifierNode,
    expressionNode: ExpressionNode?,
    keywordNode: KeywordNode,
    colonNode: ColonNode,
    variableTypeNode: VariableTypeNode,
    equalsNode: EqualsNode?,
    start: Position,
    end: Position,
): VariableDeclarationNode {
    return VariableDeclarationNode(
        identifierNode,
        expressionNode,
        keywordNode,
        colonNode,
        variableTypeNode,
        equalsNode,
        start,
        end,
    )
}

fun createNewSumNode(
    newLeftNode: ExpressionNode,
    newRightNode: ExpressionNode,
    operatorNode: OperatorNode,
    statementNode: SumNode,
): SumNode {
    return SumNode(newLeftNode, newRightNode, operatorNode, statementNode.getStart(), statementNode.getEnd())
}

fun createNewSubtractionNode(
    newLeftNode: ExpressionNode,
    newRightNode: ExpressionNode,
    operatorNode: OperatorNode,
    statementNode: SubtractionNode,
): SubtractionNode {
    return SubtractionNode(newLeftNode, newRightNode, operatorNode, statementNode.getStart(), statementNode.getEnd())
}

fun createNewProductNode(
    newLeftNode: ExpressionNode,
    newRightNode: ExpressionNode,
    operatorNode: OperatorNode,
    statementNode: ProductNode,
): ProductNode {
    return ProductNode(newLeftNode, newRightNode, operatorNode, statementNode.getStart(), statementNode.getEnd())
}

fun createNewDivisionNode(
    newLeftNode: ExpressionNode,
    newRightNode: ExpressionNode,
    operatorNode: OperatorNode,
    statementNode: DivisionNode,
): DivisionNode {
    return DivisionNode(newLeftNode, newRightNode, operatorNode, statementNode.getStart(), statementNode.getEnd())
}

fun changeExpressionNodeLine(
    expressionNode: ExpressionNode,
    newLine: Int,
): ExpressionNode {
    val newStartPosition = Position(newLine, expressionNode.getStart().column)
    val newEndPosition = Position(newLine, expressionNode.getEnd().column)
    when (expressionNode) {
        is LiteralNode<*> -> {
            return LiteralNode(expressionNode.value, newStartPosition, newEndPosition)
        }

        is SumNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.left, newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.right, newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.operatorNode.getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.operatorNode.getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return SumNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is SubtractionNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.left, newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.right, newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.operatorNode.getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.operatorNode.getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return SubtractionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is ProductNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.left, newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.right, newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.operatorNode.getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.operatorNode.getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return ProductNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is DivisionNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.left, newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.right, newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.operatorNode.getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.operatorNode.getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return DivisionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is IdentifierNode -> {
            return IdentifierNode(expressionNode.variableName, expressionNode.variableType, newStartPosition, newEndPosition)
        }

        else -> return expressionNode
    }
}

fun changeExpressionNodeColumn(
    expressionNode: ExpressionNode,
    spacesToMoveColumn: Int,
): ExpressionNode {
    val newStartPosition = Position(expressionNode.getStart().line, expressionNode.getStart().column + spacesToMoveColumn)
    val newEndPosition = Position(expressionNode.getEnd().line, expressionNode.getEnd().column + spacesToMoveColumn)
    when (expressionNode) {
        is LiteralNode<*> -> {
            return LiteralNode(expressionNode.value, newStartPosition, newEndPosition)
        }
        is SumNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.left, spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.right, spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.operatorNode.getStart().line, expressionNode.operatorNode.getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.operatorNode.getEnd().line, expressionNode.operatorNode.getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return SumNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is SubtractionNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.left, spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.right, spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.operatorNode.getStart().line, expressionNode.operatorNode.getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.operatorNode.getEnd().line, expressionNode.operatorNode.getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return SubtractionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is ProductNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.left, spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.right, spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.operatorNode.getStart().line, expressionNode.operatorNode.getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.operatorNode.getEnd().line, expressionNode.operatorNode.getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return ProductNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is DivisionNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.left, spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.right, spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.operatorNode.getStart().line, expressionNode.operatorNode.getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.operatorNode.getEnd().line, expressionNode.operatorNode.getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.operatorNode.getType())
            return DivisionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is IdentifierNode -> {
            return IdentifierNode(expressionNode.variableName, expressionNode.variableType, newStartPosition, newEndPosition)
        }
        else -> return expressionNode
    }
}

fun handleExpressionNodeWhitespaces(statementNode: ExpressionNode): ExpressionNode {
    when (statementNode) {
        is LiteralNode<*> -> {
            handleLiteralNodeWhitespaces(statementNode)
        }
        is SumNode -> {
            handleSumNodeWhitespaces(statementNode)
        }

        is SubtractionNode -> {
            handleSubtractionNodeWhitespaces(statementNode)
        }
        is ProductNode -> {
            handleProductNodeWhitespaces(statementNode)
        }
        is DivisionNode -> {
            handleDivisionNodeWhitespaces(statementNode)
        }

        is IdentifierNode -> {
            handleIdentifierNodeWhitespaces(statementNode)
        }

        else
        -> return statementNode
    }
    return statementNode
}

fun handleSumNodeWhitespaces(statementNode: SumNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.left)
    val rightNode = handleExpressionNodeWhitespaces(statementNode.right)
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

fun areThereAlreadySpaces(
    leftNodeHasSpace: Boolean,
    rightNodeHasSpace: Boolean,
): Boolean {
    return leftNodeHasSpace && rightNodeHasSpace
}

fun handleSubtractionNodeWhitespaces(statementNode: SubtractionNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.left)
    val rightNode = handleExpressionNodeWhitespaces(statementNode.right)
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

fun handleProductNodeWhitespaces(statementNode: ProductNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.left)
    val rightNode = handleExpressionNodeWhitespaces(statementNode.right)
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

fun handleDivisionNodeWhitespaces(statementNode: DivisionNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.left)
    val rightNode = handleExpressionNodeWhitespaces(statementNode.right)
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

fun handleIdentifierNodeWhitespaces(statementNode: IdentifierNode): IdentifierNode {
    return statementNode
}

fun spaceBeforeRightNode(
    rightNode: ExpressionNode,
    operatorNode: OperatorNode,
): Boolean {
    val rightNodeHasSpace = rightNode.getStart().column == operatorNode.getEnd().column + 2
    return rightNodeHasSpace
}

fun spaceAfterLeftNode(
    leftNode: ExpressionNode,
    operatorNode: OperatorNode,
): Boolean {
    val leftNodeHasSpace = leftNode.getEnd().column == operatorNode.getStart().column - 2
    return leftNodeHasSpace
}

fun handleLiteralNodeWhitespaces(statementNode: LiteralNode<*>): LiteralNode<*> {
    return statementNode
}
