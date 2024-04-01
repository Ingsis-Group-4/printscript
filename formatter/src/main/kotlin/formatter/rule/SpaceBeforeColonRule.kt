package formatter.rule

import ast.ColonNode
import ast.StatementNode
import ast.VariableDeclarationNode
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
        } else {
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
}
// dahbda : String
// 1234567890123456
// dahbda : String
