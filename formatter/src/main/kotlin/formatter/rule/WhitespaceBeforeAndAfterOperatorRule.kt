package formatter.rule

import ast.AssignationNode
import ast.StatementNode
import ast.VariableDeclarationNode
import formatter.utils.createNewAssignationNode
import formatter.utils.createNewVariableDeclarationNode
import formatter.utils.handleExpressionNodeWhitespaces

class WhitespaceBeforeAndAfterOperatorRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        val statementNode = statements[currentStatementIndex]
        if (!hasSpace) {
            return statements
        }
        return when (statementNode) {
            is VariableDeclarationNode -> {
                if (statementNode.expression != null) {
                    val auxStatementList = statements.toMutableList()
                    val newVariableDeclarationNode =
                        createNewVariableDeclarationNode(
                            statementNode.identifier,
                            handleExpressionNodeWhitespaces(statementNode.expression!!),
                            statementNode.keywordNode,
                            statementNode.colonNode,
                            statementNode.typeNode,
                            statementNode.equalsNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                    auxStatementList
                } else {
                    statements
                }
            }

            is AssignationNode -> {
                val auxStatementList = statements.toMutableList()
                val newAssignationNode =
                    createNewAssignationNode(
                        statementNode.identifier,
                        handleExpressionNodeWhitespaces(statementNode.expression),
                        statementNode.equalsNode,
                        statementNode.getStart(),
                        statementNode.getEnd(),
                    )
                auxStatementList[currentStatementIndex] = newAssignationNode
                auxStatementList
            }

            else -> statements
        }
    }
}
