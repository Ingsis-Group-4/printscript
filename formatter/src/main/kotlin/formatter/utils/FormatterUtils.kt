package formatter.utils

import ast.AssignationNode
import ast.ColonNode
import ast.DivisionNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.KeywordNode
import ast.LiteralNode
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import ast.VariableDeclarationNode
import ast.EqualsNode
import position.Position

fun createNewExpressionNode(
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
    equalsNode: EqualsNode?,
    start: Position,
    end: Position,
): VariableDeclarationNode {
    return VariableDeclarationNode(identifierNode, expressionNode, keywordNode, colonNode, equalsNode, start, end)
}
