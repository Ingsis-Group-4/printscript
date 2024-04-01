package formatter.rule

import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.KeywordNode
import ast.LetNode
import ast.PrintLnNode
import ast.StatementNode
import ast.VariableDeclarationNode
import ast.VariableTypeNode
import formatter.utils.changeExpressionNodeLine
import formatter.utils.createNewAssignationNode
import formatter.utils.createNewVariableDeclarationNode
import position.Position

class LineBreakAfterSemicolonRule(private val hasLineBreak: Boolean) : Rule {
    override fun apply(
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        if (!hasLineBreak) {
            return statementNode
        }
        // I have to check if the statement is the first one
        if (index == 0) {
            return statementNode
        }
        val statementNodeLine = statementNode.getEnd().line
        val previousStatement = statements[index - 1]
        val previousStatementLine = previousStatement.getStart().line
        if (statementNodeLine > previousStatementLine) {
            return statementNode
        }
        val newLinePosition = statementNodeLine + 1
        when (statementNode) {
            is VariableDeclarationNode -> {
                val newVariableDeclarationNode = changeVariableDeclarationNodeLine(statementNode, newLinePosition)
                return newVariableDeclarationNode
            }
            is AssignationNode -> {
                val newAssignationNode = changeAssignationNodeLine(statementNode, newLinePosition)
                return newAssignationNode
            }
            is PrintLnNode -> {
                val newPrintLnNode = changePrintLnNodeLine(statementNode, newLinePosition)
                return newPrintLnNode
            }
        }
    }

    private fun changeVariableDeclarationNodeLine(
        variableDeclarationNode: VariableDeclarationNode,
        newLine: Int,
    ): VariableDeclarationNode {
        val newStartPosition = Position(newLine, variableDeclarationNode.getStart().column)
        val newEndPosition = Position(newLine, variableDeclarationNode.getEnd().column)
        val newColonNode = changeColonNodeLine(variableDeclarationNode.colonNode, newLine)
        val newLetNode = changeLetNodeLine(variableDeclarationNode.keywordNode, newLine)
        val newVariableTypeNode = changeVariableTypeNodeLine(variableDeclarationNode.typeNode, newLine)
        var newEqualsNode: EqualsNode? = null
        if (variableDeclarationNode.equalsNode != null) {
            newEqualsNode = changeEqualsNodeLine(variableDeclarationNode.equalsNode!!, newLine)
        }
        var newExpressionNode: ExpressionNode? = null
        if (variableDeclarationNode.expression != null) {
            newExpressionNode = changeExpressionNodeLine(variableDeclarationNode.expression!!, newLine)
        }
        val newIdentifierNode = changeIdentifierNodeLine(variableDeclarationNode.identifier, newLine)
        return createNewVariableDeclarationNode(
            newIdentifierNode,
            newExpressionNode,
            newLetNode,
            newColonNode,
            newVariableTypeNode,
            newEqualsNode,
            newStartPosition,
            newEndPosition,
        )
    }

    private fun changeColonNodeLine(
        colonNode: ColonNode,
        newLine: Int,
    ): ColonNode {
        val newStartPosition = Position(newLine, colonNode.getStart().column)
        val newEndPosition = Position(newLine, colonNode.getEnd().column)
        return ColonNode(newStartPosition, newEndPosition)
    }

    private fun changeEqualsNodeLine(
        equalsNode: EqualsNode,
        newLine: Int,
    ): EqualsNode {
        val newStartPosition = Position(newLine, equalsNode.getStart().column)
        val newEndPosition = Position(newLine, equalsNode.getEnd().column)
        return EqualsNode(newStartPosition, newEndPosition)
    }

    private fun changeLetNodeLine(
        keywordNode: KeywordNode,
        newLine: Int,
    ): KeywordNode {
        val newStartPosition = Position(newLine, keywordNode.getStart().column)
        val newEndPosition = Position(newLine, keywordNode.getEnd().column)
        return LetNode(newStartPosition, newEndPosition)
    }

    private fun changeIdentifierNodeLine(
        identifierNode: IdentifierNode,
        newLine: Int,
    ): IdentifierNode {
        val newStartPosition = Position(newLine, identifierNode.getStart().column)
        val newEndPosition = Position(newLine, identifierNode.getEnd().column)
        return IdentifierNode(identifierNode.variableName, identifierNode.variableType, newStartPosition, newEndPosition)
    }

    private fun changeAssignationNodeLine(
        assignationNode: AssignationNode,
        newLine: Int,
    ): AssignationNode {
        val newStartPosition = Position(newLine, assignationNode.getStart().column)
        val newEndPosition = Position(newLine, assignationNode.getEnd().column)
        val newIdentifierNode = changeIdentifierNodeLine(assignationNode.identifier, newLine)
        val newExpressionNode = changeExpressionNodeLine(assignationNode.expression, newLine)
        val newEqualsNode = changeEqualsNodeLine(assignationNode.equalsNode, newLine)
        return createNewAssignationNode(newIdentifierNode, newExpressionNode, newEqualsNode, newStartPosition, newEndPosition)
    }

    private fun changePrintLnNodeLine(
        printLnNode: PrintLnNode,
        newLine: Int,
    ): PrintLnNode {
        val newStartPosition = Position(newLine, printLnNode.getStart().column)
        val newEndPosition = Position(newLine, printLnNode.getEnd().column)
        val newExpressionNode = changeExpressionNodeLine(printLnNode.expression, newLine)
        return PrintLnNode(newExpressionNode, newStartPosition, newEndPosition)
    }

    private fun changeVariableTypeNodeLine(
        variableTypeNode: VariableTypeNode,
        newLine: Int,
    ): VariableTypeNode {
        val newStartPosition = Position(newLine, variableTypeNode.getStart().column)
        val newEndPosition = Position(newLine, variableTypeNode.getEnd().column)
        return VariableTypeNode(variableTypeNode.variableType, newStartPosition, newEndPosition)
    }
}
