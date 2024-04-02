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
        val whiteSpacesBeforeIdentifier = " ".repeat(assignationNode.getStart().column)
        val whiteSpacesBetweenIdentifierAndEquals =
            " ".repeat(
                assignationNode.identifier.getEnd().column - assignationNode.equalsNode.getStart().column,
            )
        val rightSide = handleRightSideStatementNode(assignationNode.expression, assignationNode.equalsNode, assignationNode.getEnd())
        return whiteSpacesBeforeIdentifier +
            assignationNode.identifier.variableName +
            whiteSpacesBetweenIdentifierAndEquals +
            "=" +
            rightSide
    }

    private fun handleVariableDeclarationNode(variableDeclarationNode: VariableDeclarationNode): String {
        val whiteSpacesBeforeKeyword = " ".repeat(variableDeclarationNode.keywordNode.getStart().column)
        val whiteSpacesBetweenKeywordAndIdentifier =
            " ".repeat(
                variableDeclarationNode.identifier.getStart().column - variableDeclarationNode.keywordNode.getEnd().column,
            )
        val whiteSpacesBetweenIdentifierAndColon =
            " ".repeat(
                variableDeclarationNode.colonNode.getStart().column - variableDeclarationNode.identifier.getEnd().column,
            )
        val whiteSpacesBetweenColonAndType =
            " ".repeat(
                variableDeclarationNode.typeNode.getStart().column - variableDeclarationNode.colonNode.getEnd().column,
            )
        if (hasExpression(variableDeclarationNode)) {
            val whiteSpacesAfterType =
                " ".repeat(
                    variableDeclarationNode.getEnd().column - variableDeclarationNode.typeNode.getEnd().column,
                )
            return whiteSpacesBeforeKeyword +
                variableDeclarationNode.keywordNode.getKeyword() +
                whiteSpacesBetweenKeywordAndIdentifier +
                variableDeclarationNode.identifier.variableName +
                whiteSpacesBetweenIdentifierAndColon +
                ":" +
                whiteSpacesBetweenColonAndType +
                variableDeclarationNode.typeNode.variableType.name +
                whiteSpacesAfterType
        }
        val whiteSpacesBetweenTypeAndEquals =
            " ".repeat(
                variableDeclarationNode.equalsNode!!.getStart().column - variableDeclarationNode.typeNode.getEnd().column,
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
            variableDeclarationNode.typeNode.variableType.name +
            whiteSpacesBetweenTypeAndEquals +
            "=" +
            rightSide
    }

    private fun handleRightSideStatementNode(
        expressionNode: ExpressionNode,
        equalsNode: EqualsNode,
        endPosition: Position,
    ): String {
        val whiteSpacesBetweenEqualsAndExpression = " ".repeat(expressionNode.getStart().column - equalsNode.getEnd().column)
        val expressionNodeString = ExpressionNodeStringifier().stringify(expressionNode)
        val whiteSpacesAfterExpression = " ".repeat(endPosition.column - expressionNode.getEnd().column)
        return whiteSpacesBetweenEqualsAndExpression + expressionNodeString + whiteSpacesAfterExpression
    }

    private fun hasExpression(variableDeclarationNode: VariableDeclarationNode) = variableDeclarationNode.expression == null
}
