package formatter.rule


import ast.StatementNode
import ast.VariableDeclarationNode
import ast.ColonNode
import position.Position

class SpaceBeforeColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(statementNode: StatementNode): StatementNode {
        when (statementNode) {
            is VariableDeclarationNode -> {
                val colonPosition = statementNode.colonNode.getStart()
                val identifierFinalPosition = statementNode.identifier.getEnd()
                return if (!hasSpace) {
                    applyNoSpaceRule(colonPosition, identifierFinalPosition, statementNode)
                } else {
                    applySpaceRule(colonPosition, identifierFinalPosition, statementNode)
                }
            }

            else -> return statementNode
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
            return createNewVariableDeclarationNode(newColonNode, statementNode)
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
            createNewVariableDeclarationNode(newColonNode, statementNode)
        }
    }

    private fun createNewVariableDeclarationNode(
        newColonNode: ColonNode,
        statementNode: VariableDeclarationNode,
    ): VariableDeclarationNode {
        return VariableDeclarationNode(
            statementNode.identifier,
            statementNode.expression,
            statementNode.keywordNode,
            newColonNode,
            statementNode.equalsNode,
            statementNode.getStart(),
            statementNode.getEnd(),
        )
    }
}
// dahbda : String
// 1234567890123456
// dahbda : String
