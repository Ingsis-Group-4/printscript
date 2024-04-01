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
