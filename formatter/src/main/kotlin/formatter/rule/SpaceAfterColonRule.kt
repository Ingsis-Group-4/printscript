package formatter.rule

import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import ast.EqualsNode
import position.Position

class SpaceAfterColonRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        when (val statementNode = statements[currentStatementIndex]) {
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
                        auxStatementList[currentStatementIndex] = newStatementNode
                        auxStatementList
                    }
                } else {
                    if (colonPosition.column == typePosition.column - 2) {
                        statements
                    } else {
                        val auxStatementList = statements.toMutableList()
                        val newStatementNode = applySpaceRule(typePosition, colonPosition, statementNode)
                        auxStatementList[currentStatementIndex] = newStatementNode
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
        if (colonPosition.column+2 <  typePosition.column ){
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
        )}
        else{
            return moveNodesAfterTypeNode(typePosition, colonPosition, statementNode)
        }
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
    private fun moveNodesAfterTypeNode(typePosition: Position, colonPosition: Position, statementNode: VariableDeclarationNode):VariableDeclarationNode{
        val newTypeStartPosition = Position(typePosition.line, colonPosition.column + 2)
        val newTypeEndPosition =
            Position(typePosition.line, colonPosition.column + getTypeNameLength(statementNode) + 1)
        val newTypeNode = createNewTypeNode(statementNode, newTypeStartPosition, newTypeEndPosition)
        if (statementNode.equalsNode == null || statementNode.expression == null){
            return formatter.utils.createNewVariableDeclarationNode(
                statementNode.identifier,
                statementNode.expression,
                statementNode.keywordNode,
                statementNode.colonNode,
                newTypeNode,
                statementNode.equalsNode,
                statementNode.getStart(),
                newTypeEndPosition,
            )
        }
        else{
            val newEqualsNodeStartPosition = Position(statementNode.equalsNode!!.getStart().line, statementNode.equalsNode!!.getStart().column + 1)
            val newEqualsNodeEndPosition = Position(statementNode.equalsNode!!.getEnd().line, statementNode.equalsNode!!.getEnd().column + 1)
            val newEqualsNode = EqualsNode(newEqualsNodeStartPosition, newEqualsNodeEndPosition)
            val newExpressionNode = formatter.utils.changeExpressionNodeColumn(statementNode.expression!!, 1)
            val newEndPosition = newExpressionNode.getEnd()
            return formatter.utils.createNewVariableDeclarationNode(
                statementNode.identifier,
                newExpressionNode,
                statementNode.keywordNode,
                statementNode.colonNode,
                newTypeNode,
                newEqualsNode,
                statementNode.getStart(),
                newEndPosition,
            )
        }
    }
}
