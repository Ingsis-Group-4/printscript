package formatter.rule

import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import position.Position

class SpaceAfterColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        val statementNode = statements[currentIndex]
        when (statementNode) {
            is VariableDeclarationNode -> {
                if (statementNode.identifier.variableType == null) {
                    return statements
                }
                val colonPosition = statementNode.colonNode.getEnd()
                val typePosition = statementNode.typeNode.getStart()
                return if (!hasSpace) {
                    if (colonPosition.column == typePosition.column - 1) {
                        statements
                    } else {
                        val auxStatementList = statements.toMutableList()
                        val newStatementNode = applyNoSpaceRule(typePosition, colonPosition, statementNode)
                        auxStatementList[currentIndex] = newStatementNode
                        auxStatementList
                    }
                } else {
                    if (colonPosition.column == typePosition.column - 2) {
                        statements
                    } else {
                        val auxStatementList = statements.toMutableList()
                        val newStatementNode = applySpaceRule(typePosition, colonPosition, statementNode)
                        auxStatementList[currentIndex] = newStatementNode
                        auxStatementList
                    }
                }
            }

            else -> {
                return statements
            }
        }
    }

    private fun applySpaceRule(
        typePosition: Position,
        colonPosition: Position,
        statementNode: VariableDeclarationNode,
    ): VariableDeclarationNode {
        val newTypeStartPosition = Position(typePosition.line, colonPosition.column + 2)
        val newTypeEndPosition =
            Position(typePosition.line, colonPosition.column + getTypeNameLength(statementNode) + 1)
        val newTypeNode = createNewTypeNode(statementNode, newTypeStartPosition, newTypeEndPosition)
        return formatter.utils.createNewVariableDeclarationNode(
            statementNode.identifier,
            statementNode.expression,
            statementNode.keywordNode,
            statementNode.colonNode,
            newTypeNode,
            statementNode.equalsNode,
            statementNode.getStart(),
            statementNode.getEnd(),
        )
    }

    private fun applyNoSpaceRule(
        typePosition: Position,
        colonPosition: Position,
        statementNode: VariableDeclarationNode,
    ): VariableDeclarationNode {
        val newTypeStartPosition = Position(typePosition.line, colonPosition.column + 1)
        val newTypeEndPosition =
            Position(typePosition.line, colonPosition.column + getTypeNameLength(statementNode))
        val newTypeNode = createNewTypeNode(statementNode, newTypeStartPosition, newTypeEndPosition)
        return formatter.utils.createNewVariableDeclarationNode(
            statementNode.identifier,
            statementNode.expression,
            statementNode.keywordNode,
            statementNode.colonNode,
            newTypeNode,
            statementNode.equalsNode,
            statementNode.getStart(),
            statementNode.getEnd(),
        )
    }

    private fun getTypeNameLength(statementNode: VariableDeclarationNode): Int {
        val typeName = statementNode.identifier.variableType.toString()
        val typeLength = typeName.length
        return typeLength
    }

    private fun createNewTypeNode(
        statementNode: VariableDeclarationNode,
        newTypeStartPosition: Position,
        newTypeEndPosition: Position,
    ): VariableTypeNode {
        val newTypeNode =
            VariableTypeNode(
                statementNode.typeNode.variableType,
                newTypeStartPosition,
                newTypeEndPosition,
            )
        return newTypeNode
    }
}
