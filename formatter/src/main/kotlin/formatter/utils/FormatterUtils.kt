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
            return LiteralNode(expressionNode.value, newExpressionNodeStartPosition, newExpressionNodeEndPosition)
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
                expressionNode.getLeft(),
                expressionNode.getRight(),
                expressionNode.getOperator(),
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is ProductNode -> {
            return ProductNode(
                expressionNode.getLeft(),
                expressionNode.getRight(),
                expressionNode.getOperator(),
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is DivisionNode -> {
            return DivisionNode(
                expressionNode.getLeft(),
                expressionNode.getRight(),
                expressionNode.getOperator(),
                newExpressionNodeStartPosition,
                newExpressionNodeEndPosition,
            )
        }

        is SubtractionNode -> {
            return SubtractionNode(
                expressionNode.getLeft(),
                expressionNode.getRight(),
                expressionNode.getOperator(),
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
            val newLeftNode = changeExpressionNodeLine(expressionNode.getLeft(), newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.getRight(), newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.getOperator().getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.getOperator().getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return SumNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is SubtractionNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.getLeft(), newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.getRight(), newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.getOperator().getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.getOperator().getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return SubtractionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is ProductNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.getLeft(), newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.getRight(), newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.getOperator().getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.getOperator().getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return ProductNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }

        is DivisionNode -> {
            val newLeftNode = changeExpressionNodeLine(expressionNode.getLeft(), newLine)
            val newRightNode = changeExpressionNodeLine(expressionNode.getRight(), newLine)
            val newOperatorNodeStart = Position(newLine, expressionNode.getOperator().getStart().column)
            val newOperatorNodeEnd = Position(newLine, expressionNode.getOperator().getEnd().column)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
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
            val newLeftNode = changeExpressionNodeColumn(expressionNode.getLeft(), spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.getRight(), spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.getOperator().getStart().line, expressionNode.getOperator().getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.getOperator().getEnd().line, expressionNode.getOperator().getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return SumNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is SubtractionNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.getLeft(), spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.getRight(), spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.getOperator().getStart().line, expressionNode.getOperator().getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.getOperator().getEnd().line, expressionNode.getOperator().getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return SubtractionNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is ProductNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.getLeft(), spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.getRight(), spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.getOperator().getStart().line, expressionNode.getOperator().getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.getOperator().getEnd().line, expressionNode.getOperator().getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
            return ProductNode(newLeftNode, newRightNode, newOperatorNode, newStartPosition, newEndPosition)
        }
        is DivisionNode -> {
            val newLeftNode = changeExpressionNodeColumn(expressionNode.getLeft(), spacesToMoveColumn)
            val newRightNode = changeExpressionNodeColumn(expressionNode.getRight(), spacesToMoveColumn)
            val newOperatorNodeStart =
                Position(expressionNode.getOperator().getStart().line, expressionNode.getOperator().getStart().column + spacesToMoveColumn)
            val newOperatorNodeEnd =
                Position(expressionNode.getOperator().getEnd().line, expressionNode.getOperator().getEnd().column + spacesToMoveColumn)
            val newOperatorNode =
                OperatorNode(newOperatorNodeStart, newOperatorNodeEnd, expressionNode.getOperator().getType())
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
            return handleLiteralNodeWhitespaces(statementNode)
        }
        is SumNode -> {
            return handleSumNodeWhitespaces(statementNode)
        }

        is SubtractionNode -> {
            return handleSubtractionNodeWhitespaces(statementNode)
        }
        is ProductNode -> {
            return handleProductNodeWhitespaces(statementNode)
        }
        is DivisionNode -> {
            return handleDivisionNodeWhitespaces(statementNode)
        }

        is IdentifierNode -> {
            return handleIdentifierNodeWhitespaces(statementNode)
        }

        else
        -> return statementNode
    }
}

fun handleSumNodeWhitespaces(statementNode: SumNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.getLeft())
    val rightNode = handleExpressionNodeWhitespaces(statementNode.getRight())
    val operatorNode = statementNode.getOperator()
    val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
    val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
    if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
    if (!leftNodeHasSpace){
        val differenceBetweenPositions = operatorNode.getStart().column - (leftNode.getEnd().column+2)
        val newOperatorNode = OperatorNode(Position(operatorNode.getStart().line, operatorNode.getStart().column - differenceBetweenPositions), Position(operatorNode.getEnd().line, operatorNode.getEnd().column - differenceBetweenPositions), operatorNode.getType())
        val newSumNode = SumNode(leftNode, rightNode, newOperatorNode, leftNode.getStart(), rightNode.getEnd())
        return handleSumNodeWhitespaces(newSumNode)
    }
    else {
        val differenceBetweenPositions = rightNode.getStart().column - (operatorNode.getEnd().column+2)
        val newRightNode = changeExpressionNodeColumn(rightNode, -differenceBetweenPositions)
        val newSumNode = SumNode(leftNode, newRightNode, operatorNode, leftNode.getStart(), newRightNode.getEnd())
        return newSumNode
    }
}

fun areThereAlreadySpaces(
    leftNodeHasSpace: Boolean,
    rightNodeHasSpace: Boolean,
): Boolean {
    return leftNodeHasSpace && rightNodeHasSpace
}

fun handleSubtractionNodeWhitespaces(statementNode: SubtractionNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.getLeft())
    val rightNode = handleExpressionNodeWhitespaces(statementNode.getRight())
    val operatorNode = statementNode.getOperator()
    val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
    val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
    if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
    if (!leftNodeHasSpace){
        val differenceBetweenPositions = operatorNode.getStart().column - (leftNode.getEnd().column+2)
        val newOperatorNode = OperatorNode(Position(operatorNode.getStart().line, operatorNode.getStart().column - differenceBetweenPositions), Position(operatorNode.getEnd().line, operatorNode.getEnd().column - differenceBetweenPositions), operatorNode.getType())
        val newSubtractionNode = SubtractionNode(leftNode, rightNode, newOperatorNode, leftNode.getStart(), rightNode.getEnd())
        return handleSubtractionNodeWhitespaces(newSubtractionNode)
    }
    else {
        val differenceBetweenPositions = rightNode.getStart().column - (operatorNode.getEnd().column+2)
        val newRightNode = changeExpressionNodeColumn(rightNode, -differenceBetweenPositions)
        val newSubtractionNode = SubtractionNode(leftNode, newRightNode, operatorNode, leftNode.getStart(), newRightNode.getEnd())
        return newSubtractionNode
    }
}

fun handleProductNodeWhitespaces(statementNode: ProductNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.getLeft())
    val rightNode = handleExpressionNodeWhitespaces(statementNode.getRight())
    val operatorNode = statementNode.getOperator()
    val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
    val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
    if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
    if (!leftNodeHasSpace){
        val differenceBetweenPositions = operatorNode.getStart().column - (leftNode.getEnd().column+2)
        val newOperatorNode = OperatorNode(Position(operatorNode.getStart().line, operatorNode.getStart().column - differenceBetweenPositions), Position(operatorNode.getEnd().line, operatorNode.getEnd().column - differenceBetweenPositions), operatorNode.getType())
        val newProductNode = ProductNode(leftNode, rightNode, newOperatorNode, leftNode.getStart(), rightNode.getEnd())
        return handleProductNodeWhitespaces(newProductNode)
    }
    else {
        val differenceBetweenPositions = rightNode.getStart().column - (operatorNode.getEnd().column+2)
        val newRightNode = changeExpressionNodeColumn(rightNode, -differenceBetweenPositions)
        val newProductNode = ProductNode(leftNode, newRightNode, operatorNode, leftNode.getStart(), newRightNode.getEnd())
        return newProductNode
    }
}

fun handleDivisionNodeWhitespaces(statementNode: DivisionNode): ExpressionNode {
    val leftNode = handleExpressionNodeWhitespaces(statementNode.getLeft())
    val rightNode = handleExpressionNodeWhitespaces(statementNode.getRight())
    val operatorNode = statementNode.getOperator()
    val leftNodeHasSpace = spaceAfterLeftNode(leftNode, operatorNode)
    val rightNodeHasSpace = spaceBeforeRightNode(rightNode, operatorNode)
    if (areThereAlreadySpaces(leftNodeHasSpace, rightNodeHasSpace)) return statementNode
    if (!leftNodeHasSpace){
        val differenceBetweenPositions = operatorNode.getStart().column - (leftNode.getEnd().column+2)
        val newOperatorNode = OperatorNode(Position(operatorNode.getStart().line, operatorNode.getStart().column - differenceBetweenPositions), Position(operatorNode.getEnd().line, operatorNode.getEnd().column - differenceBetweenPositions), operatorNode.getType())
        val newDivisionNode = DivisionNode(leftNode, rightNode, newOperatorNode, leftNode.getStart(), rightNode.getEnd())
        return handleDivisionNodeWhitespaces(newDivisionNode)
    }
    else {
        val differenceBetweenPositions = rightNode.getStart().column - (operatorNode.getEnd().column+2)
        val newRightNode = changeExpressionNodeColumn(rightNode, -differenceBetweenPositions)
        val newDivisionNode = DivisionNode(leftNode, newRightNode, operatorNode, leftNode.getStart(), newRightNode.getEnd())
        return newDivisionNode
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
