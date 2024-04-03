package formatter.stringifier

import ast.AST
import ast.AssignationNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.VariableDeclarationNode
import ast.VariableStatementNode
import position.Position

class VariableStatementNodeStringifier : Stringifier {
    override fun stringify(node: AST): String {
        return when (val variableStatementNode = node as VariableStatementNode) {
            is VariableDeclarationNode -> handleVariableDeclarationNode(variableStatementNode)
            is AssignationNode -> handleAssignationNode(variableStatementNode)
        }
    }

    private fun handleAssignationNode(assignationNode: AssignationNode): String {
        val whiteSpacesBeforeIdentifier = " ".repeat(assignationNode.getStart().column - 1)
        val whiteSpacesBetweenIdentifierAndEquals =
            " ".repeat(
                assignationNode.equalsNode.getStart().column - assignationNode.identifier.getEnd().column - 1,
            )
        val rightSide = handleRightSideStatementNode(assignationNode.expression, assignationNode.equalsNode, assignationNode.getEnd())
        return whiteSpacesBeforeIdentifier +
            assignationNode.identifier.variableName +
            whiteSpacesBetweenIdentifierAndEquals +
            "=" +
            rightSide
    }

    private fun handleVariableDeclarationNode(variableDeclarationNode: VariableDeclarationNode): String {
        val whiteSpacesBeforeKeyword = " ".repeat(variableDeclarationNode.keywordNode.getStart().column - 1)
        val whiteSpacesBetweenKeywordAndIdentifier =
            " ".repeat(
                variableDeclarationNode.identifier.getStart().column - variableDeclarationNode.keywordNode.getEnd().column - 1,
            )
        val whiteSpacesBetweenIdentifierAndColon =
            " ".repeat(
                variableDeclarationNode.colonNode.getStart().column - variableDeclarationNode.identifier.getEnd().column - 1,
            )
        val whiteSpacesBetweenColonAndType =
            " ".repeat(
                variableDeclarationNode.typeNode.getStart().column - variableDeclarationNode.colonNode.getEnd().column - 1,
            )
        if (!hasExpression(variableDeclarationNode)) {
            val whiteSpacesAfterType =
                " ".repeat(
                    variableDeclarationNode.getEnd().column - variableDeclarationNode.typeNode.getEnd().column - 1,
                )
            return whiteSpacesBeforeKeyword +
                variableDeclarationNode.keywordNode.getKeyword() +
                whiteSpacesBetweenKeywordAndIdentifier +
                variableDeclarationNode.identifier.variableName +
                whiteSpacesBetweenIdentifierAndColon +
                ":" +
                whiteSpacesBetweenColonAndType +
                variableDeclarationNode.typeNode.getVariableType() +
                whiteSpacesAfterType
        }
        val whiteSpacesBetweenTypeAndEquals =
            " ".repeat(
                variableDeclarationNode.equalsNode!!.getStart().column - variableDeclarationNode.typeNode.getEnd().column - 1,
            )
        val rightSide =
            handleRightSideStatementNode(
                variableDeclarationNode.expression!!,
                variableDeclarationNode.equalsNode!!,
                variableDeclarationNode.getEnd(),
            )
        return whiteSpacesBeforeKeyword +
            variableDeclarationNode.keywordNode.getKeyword() +
            whiteSpacesBetweenKeywordAndIdentifier +
            variableDeclarationNode.identifier.variableName +
            whiteSpacesBetweenIdentifierAndColon +
            ":" +
            whiteSpacesBetweenColonAndType +
            variableDeclarationNode.typeNode.getVariableType() +
            whiteSpacesBetweenTypeAndEquals +
            "=" +
            rightSide
    }

    private fun handleRightSideStatementNode(
        expressionNode: ExpressionNode,
        equalsNode: EqualsNode,
        endPosition: Position,
    ): String {
        val whiteSpacesBetweenEqualsAndExpression = " ".repeat(expressionNode.getStart().column - equalsNode.getEnd().column - 1)
        val expressionNodeString = ExpressionNodeStringifier().stringify(expressionNode)
        val whiteSpacesAfterExpression = " ".repeat(endPosition.column - expressionNode.getEnd().column - 1)
        return whiteSpacesBetweenEqualsAndExpression + expressionNodeString + whiteSpacesAfterExpression
    }

    private fun hasExpression(variableDeclarationNode: VariableDeclarationNode) = variableDeclarationNode.expression != null
}
