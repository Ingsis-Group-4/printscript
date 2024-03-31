package formatter.rule

import ast.IdentifierNode
import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import position.Position

class SpaceAfterColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        when (statementNode) {
            is VariableDeclarationNode -> {
                if (statementNode.identifier.variableType == null) {
                    return statementNode
                }
                val colonPosition = statementNode.colonNode.getEnd()
                val typePosition = statementNode.identifier.variableType?.getStart()
                return if (typePosition == null) {
                    statementNode
                } else if (!hasSpace) {
                    if (colonPosition.column == typePosition.column - 1) {
                        statementNode
                    } else {
                        applyNoSpaceRule(typePosition, colonPosition, statementNode)
                    }
                } else {
                    if (colonPosition.column == typePosition.column - 2) {
                        statementNode
                    } else {
                        applySpaceRule(typePosition, colonPosition, statementNode)
                    }
                }
            }
            else -> {
                return statementNode
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
        val newIdentifierNode = createNewIdentifierNode(statementNode, newTypeNode)
        return formatter.utils.createNewVariableDeclarationNode(
            newIdentifierNode,
            statementNode.expression,
            statementNode.keywordNode,
            statementNode.colonNode,
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
        val newIdentifierNode = createNewIdentifierNode(statementNode, newTypeNode)
        return formatter.utils.createNewVariableDeclarationNode(
            newIdentifierNode,
            statementNode.expression,
            statementNode.keywordNode,
            statementNode.colonNode,
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

    private fun createNewIdentifierNode(
        statementNode: VariableDeclarationNode,
        newTypeNode: VariableTypeNode,
    ): IdentifierNode {
        val newIdentifierNode =
            IdentifierNode(
                statementNode.identifier.variableName,
                newTypeNode,
                statementNode.identifier.getStart(),
                statementNode.identifier.getEnd(),
            )
        return newIdentifierNode
    }

    private fun createNewTypeNode(
        statementNode: VariableDeclarationNode,
        newTypeStartPosition: Position,
        newTypeEndPosition: Position,
    ): VariableTypeNode {
        val newTypeNode =
            VariableTypeNode(
                statementNode.identifier.variableType!!.variableType,
                newTypeStartPosition,
                newTypeEndPosition,
            )
        return newTypeNode
    }
}
