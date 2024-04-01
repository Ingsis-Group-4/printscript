package formatter.rule

import ast.AST
import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.PrintLnNode
import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import formatter.utils.changeExpressionNodeColumn
import formatter.utils.createNewVariableDeclarationNode
import formatter.utils.handleExpressionNodeWhitespaces
import position.Position

class WhitespaceBetweenTokensRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        if (hasSpace) {
            val auxStatementList = statements.toMutableList()
            when (statements[currentStatementIndex]) {
                is AssignationNode -> {
                    val newAssignationNode =
                        handleAssignationNodeWhitespaces(statements[currentStatementIndex] as AssignationNode)
                    auxStatementList[currentStatementIndex] = newAssignationNode
                    return auxStatementList
                }

                is VariableDeclarationNode -> {
                    val newVariableDeclarationNode =
                        handleVariableDeclarationNodeWhitespaces(statements[currentStatementIndex] as VariableDeclarationNode)
                    auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                    return auxStatementList
                }
                is PrintLnNode -> {
                    val newPrintLnNode = handlePrintLnNodeWhitespaces(statements[currentStatementIndex] as PrintLnNode)
                    auxStatementList[currentStatementIndex] = newPrintLnNode
                    return auxStatementList
                }
            }
        }
        return statements
    }

    private fun handleAssignationNodeWhitespaces(node: AssignationNode): AssignationNode {
        var newExpressionNode = handleExpressionNodeWhitespaces(node.expression)
        val identifierNode = node.identifier
        val equalsNode = node.equalsNode
        var newEqualsNode = equalsNode
        if (shouldReduceSpacesBetweenNodes(identifierNode, equalsNode)) {
            val newEqualsNodeStartPosition = Position(equalsNode.getStart().line, identifierNode.getEnd().column + 2)
            val newEqualsNodeEndPosition = Position(equalsNode.getEnd().line, equalsNode.getEnd().column - 2)
            newEqualsNode = EqualsNode(newEqualsNodeStartPosition, newEqualsNodeEndPosition)
        }
        if (shouldReduceSpacesBetweenNodes(newEqualsNode, newExpressionNode)) {
            val spacesToMoveExpressionNode = spacesToMove(newEqualsNode, newExpressionNode)
            newExpressionNode = changeExpressionNodeColumn(newExpressionNode, spacesToMoveExpressionNode)
        }
        return AssignationNode(identifierNode, newExpressionNode, newEqualsNode, node.getStart(), node.getEnd())
    }

    private fun handleVariableDeclarationNodeWhitespaces(node: VariableDeclarationNode): VariableDeclarationNode {
        val keywordNode = node.keywordNode
        var identifierNode = node.identifier
        if (shouldReduceSpacesBetweenNodes(keywordNode, identifierNode)) {
            val spacesToMove = spacesToMove(keywordNode, identifierNode)
            val newIdentifierNodeStartPosition =
                Position(identifierNode.getStart().line, identifierNode.getStart().column - spacesToMove)
            val newIdentifierNodeEndPosition = Position(identifierNode.getEnd().line, identifierNode.getEnd().column - spacesToMove)
            identifierNode =
                IdentifierNode(
                    identifierNode.variableName,
                    identifierNode.variableType,
                    newIdentifierNodeStartPosition,
                    newIdentifierNodeEndPosition,
                )
        }
        var colonNode = node.colonNode
        if (shouldReduceSpacesBetweenNodes(identifierNode, colonNode)) {
            val spacesToMove = spacesToMove(identifierNode, colonNode)
            val newColonNodeStartPosition = Position(colonNode.getStart().line, colonNode.getStart().column - spacesToMove)
            val newColonNodeEndPosition = Position(colonNode.getEnd().line, colonNode.getEnd().column - spacesToMove)
            colonNode = ColonNode(newColonNodeStartPosition, newColonNodeEndPosition)
        }
        var typeNode = node.typeNode
        if (shouldReduceSpacesBetweenNodes(colonNode, typeNode)) {
            val spacesToMove = spacesToMove(colonNode, typeNode)
            val newTypeNodeStartPosition = Position(typeNode.getStart().line, typeNode.getStart().column - spacesToMove)
            val newTypeNodeEndPosition = Position(typeNode.getEnd().line, typeNode.getEnd().column - spacesToMove)
            typeNode = VariableTypeNode(typeNode.variableType, newTypeNodeStartPosition, newTypeNodeEndPosition)
        }
        if (node.equalsNode != null && node.expression != null) {
            var equalsNode = node.equalsNode
            var expressionNode = handleExpressionNodeWhitespaces(node.expression!!)
            if (shouldReduceSpacesBetweenNodes(typeNode, equalsNode!!)) {
                val spacesToMove = spacesToMove(typeNode, equalsNode)
                val newEqualsNodeStartPosition = Position(equalsNode.getStart().line, equalsNode.getStart().column - spacesToMove)
                val newEqualsNodeEndPosition = Position(equalsNode.getEnd().line, equalsNode.getEnd().column - spacesToMove)
                equalsNode = EqualsNode(newEqualsNodeStartPosition, newEqualsNodeEndPosition)
            }
            if (shouldReduceSpacesBetweenNodes(equalsNode, expressionNode)) {
                val spacesToMove = spacesToMove(equalsNode, expressionNode)
                expressionNode = changeExpressionNodeColumn(expressionNode, spacesToMove)
            }
            val newEndPosition = expressionNode.getEnd()
            return createNewVariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                node.getStart(),
                newEndPosition,
            )
        } else {
            val newEndPosition = typeNode.getEnd()
            return createNewVariableDeclarationNode(
                identifierNode,
                node.expression,
                keywordNode,
                colonNode,
                typeNode,
                node.equalsNode,
                node.getStart(),
                newEndPosition,
            )
        }
    }

    private fun handlePrintLnNodeWhitespaces(node: PrintLnNode): PrintLnNode {
        var expressionNode = handleExpressionNodeWhitespaces(node.expression)
        val openParenthesisColumn = node.getStart().column + 7
        val closeParenthesisColumn = node.getEnd().column - 1
        val expressionNodeStart = expressionNode.getStart().column
        val expressionNodeEnd = expressionNode.getEnd().column
        var newEndPosition = node.getEnd()
        if (expressionNodeStart - openParenthesisColumn > 2) {
            val spacesToMove = expressionNodeStart - (openParenthesisColumn + 2)
            expressionNode = changeExpressionNodeColumn(expressionNode, spacesToMove)
        }
        if (closeParenthesisColumn - expressionNodeEnd > 2) {
            newEndPosition = Position(node.getEnd().line, expressionNodeEnd + 2)
        }
        return PrintLnNode(expressionNode, node.getStart(), newEndPosition)
    }

    private fun shouldReduceSpacesBetweenNodes(
        leftNode: AST,
        rightNode: AST,
    ): Boolean {
        val leftEndPosition = leftNode.getEnd().column
        val rightStartPosition = rightNode.getStart().column
        return rightStartPosition - leftEndPosition > 2
    }

    private fun spacesToMove(
        leftNode: AST,
        rightNode: AST,
    ): Int {
        return rightNode.getStart().column - (leftNode.getEnd().column + 2)
    }
}
