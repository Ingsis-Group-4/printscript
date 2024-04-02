package formatter.rule

import ast.ColonNode
import ast.EqualsNode
import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import position.Position

class SpaceBeforeColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        val statementNode = statements[currentStatementIndex]
        when (statementNode) {
            is VariableDeclarationNode -> {
                val colonPosition = statementNode.colonNode.getStart()
                val identifierFinalPosition = statementNode.identifier.getEnd()
                val auxStatementList = statements.toMutableList()
                return if (!hasSpace) {
                    val newVariableDeclarationNode = applyNoSpaceRule(colonPosition, identifierFinalPosition, statementNode)
                    auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                    auxStatementList
                } else {
                    val newVariableDeclarationNode = applySpaceRule(colonPosition, identifierFinalPosition, statementNode)
                    auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                    auxStatementList
                }
            }
            else -> return statements
        }
    }

    private fun applySpaceRule(
        colonPosition: Position,
        identifierFinalPosition: Position,
        statementNode: VariableDeclarationNode,
    ): StatementNode {
        if (colonPosition.column == identifierFinalPosition.column + 2) {
            return statementNode
        } else if (colonPosition.column > identifierFinalPosition.column + 2) {
            val newColonPosition = Position(colonPosition.line, identifierFinalPosition.column + 2)
            val newColonNode = createColonNode(newColonPosition, newColonPosition)
            return formatter.utils.createNewVariableDeclarationNode(
                statementNode.identifier,
                statementNode.expression,
                statementNode.keywordNode,
                newColonNode,
                statementNode.typeNode,
                statementNode.equalsNode,
                statementNode.getStart(),
                statementNode.getEnd(),
            )
        } else {
            return moveEveryNodeAfterColonNode(statementNode, identifierFinalPosition)
        }
    }

    private fun createColonNode(
        newColonStartPosition: Position,
        newColonEndPosition: Position,
    ): ColonNode {
        val newColonNode = ColonNode(newColonStartPosition, newColonEndPosition)
        return newColonNode
    }

    private fun applyNoSpaceRule(
        colonPosition: Position,
        identifierFinalPosition: Position,
        statementNode: VariableDeclarationNode,
    ): StatementNode {
        return if (colonPosition.column == identifierFinalPosition.column + 1) {
            statementNode
        } else {
            val newColonPosition = Position(colonPosition.line, identifierFinalPosition.column + 1)
            val newColonNode = createColonNode(newColonPosition, newColonPosition)
            return formatter.utils.createNewVariableDeclarationNode(
                statementNode.identifier,
                statementNode.expression,
                statementNode.keywordNode,
                newColonNode,
                statementNode.typeNode,
                statementNode.equalsNode,
                statementNode.getStart(),
                statementNode.getEnd(),
            )
        }
    }

    private fun moveEveryNodeAfterColonNode(
        node: VariableDeclarationNode,
        identifierFinalPosition: Position,
    ): VariableDeclarationNode {
        val newColonPosition = Position(node.colonNode.getStart().line, identifierFinalPosition.column + 2)
        val newColonNode = createColonNode(newColonPosition, newColonPosition)
        val newTypeNodeStartPosition = Position(node.typeNode.getStart().line, node.typeNode.getStart().column + 1)
        val newTypeNodeEndPosition = Position(node.typeNode.getEnd().line, node.typeNode.getEnd().column + 1)
        val newTypeNode = VariableTypeNode(node.typeNode.variableType, newTypeNodeStartPosition, newTypeNodeEndPosition)
        if (node.equalsNode == null || node.expression == null) {
            return formatter.utils.createNewVariableDeclarationNode(
                node.identifier,
                node.expression,
                node.keywordNode,
                newColonNode,
                newTypeNode,
                node.equalsNode,
                node.getStart(),
                newTypeNodeEndPosition,
            )
        }
        val newEqualsNodeStartPosition = Position(node.equalsNode!!.getStart().line, node.equalsNode!!.getStart().column + 1)
        val newEqualsNodeEndPosition = Position(node.equalsNode!!.getEnd().line, node.equalsNode!!.getEnd().column + 1)
        val newEqualsNode = EqualsNode(newEqualsNodeStartPosition, newEqualsNodeEndPosition)
        val newExpressionNode = formatter.utils.changeExpressionNodeColumn(node.expression!!, 1)
        val newEndPosition = newExpressionNode.getEnd()
        return formatter.utils.createNewVariableDeclarationNode(
            node.identifier,
            newExpressionNode,
            node.keywordNode,
            newColonNode,
            newTypeNode,
            newEqualsNode,
            node.getStart(),
            newEndPosition,
        )
    }
}
// dahbda : String
// 1234567890123456
// dahbda : String
