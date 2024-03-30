package formatter.rule

import ast.StatementNode
import ast.VariableDeclarationNode
import ast.FunctionStatementNode
import ast.AssignationNode
import ast.IdentifierNode
import ast.VariableTypeNode
import position.Position

class SpaceAfterColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(statementNode: StatementNode): StatementNode {
        when (statementNode) {
            is FunctionStatementNode -> {
                return statementNode
            }

            is AssignationNode -> {
                return statementNode
            }

            is VariableDeclarationNode -> {
                if (statementNode.identifier.variableType == null) {
                    return statementNode
                }
                val colonPosition = statementNode.colonNode.getEnd()
                val typePosition = statementNode.identifier.variableType?.getStart()
                if (typePosition == null) {
                    return statementNode
                } else if (!hasSpace) {
                    return if (colonPosition.column == typePosition.column - 1) {
                        statementNode
                    } else {
                        applyNoSpaceRule(typePosition, colonPosition, statementNode)
                    }
                } else {
                    if (colonPosition.column == typePosition.column - 2) {
                        return statementNode
                    } else {
                        return applySpaceRule(typePosition, colonPosition, statementNode)
                    }
                }
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
        return createNewVariableDeclarationNode(newIdentifierNode, statementNode)
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
        return createNewVariableDeclarationNode(newIdentifierNode, statementNode)
    }

    private fun getTypeNameLength(statementNode: VariableDeclarationNode): Int {
        val typeName = statementNode.identifier.variableType.toString()
        val typeLength = typeName.length
        return typeLength
    }

    private fun createNewVariableDeclarationNode(
        newIdentifierNode: IdentifierNode,
        statementNode: VariableDeclarationNode,
    ): VariableDeclarationNode {
        val newVariableDeclarationNode =
            VariableDeclarationNode(
                newIdentifierNode,
                statementNode.expression,
                statementNode.keywordNode,
                statementNode.colonNode,
                statementNode.equalsNode,
                statementNode.getStart(),
                statementNode.getEnd(),
            )
        return newVariableDeclarationNode
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
