package formatter.rule

import ast.AssignationNode
import ast.EqualsNode
import ast.FunctionStatementNode
import ast.StatementNode
import ast.VariableDeclarationNode
import formatter.utils.createNewAssignationNode
import formatter.utils.createNewVariableDeclarationNode
import position.Position

class SpaceBeforeEqualSignRule(private val hasSpace: Boolean) : Rule {
    override fun apply(
        currentStatementIndex: Int,
        statements: List<StatementNode>,
    ): List<StatementNode> {
        when (val statementNode = statements[currentStatementIndex]) {
            is FunctionStatementNode -> {
                return statements
            }
            is AssignationNode -> {
                val equalSignPosition = statementNode.equalsNode.getStart()
                val endPosition = getEndPositionAssignationNode(statementNode)
                if (!hasSpace) {
                    if (endPosition.column == equalSignPosition.column - 1) {
                        return statements
                    } else {
                        val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 1)
                        val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                        val auxStatementList = statements.toMutableList()
                        val newAssignationNode =
                            createNewAssignationNode(
                                statementNode.identifier,
                                statementNode.expression,
                                newEqualSignNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newAssignationNode
                        return auxStatementList
                    }
                } else {
                    if (endPosition.column == equalSignPosition.column - 2) {
                        return statements
                    } else {
                        val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 2)
                        val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                        val auxStatementList = statements.toMutableList()
                        val newAssignationNode =
                            createNewAssignationNode(
                                statementNode.identifier,
                                statementNode.expression,
                                newEqualSignNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        auxStatementList[currentStatementIndex] = newAssignationNode
                        return auxStatementList
                    }
                }
            }
            is VariableDeclarationNode -> {
                if (statementNode.equalsNode == null) {
                    return statements
                } else {
                    val equalSignPosition = statementNode.equalsNode!!.getStart()
                    val endPosition = getEndPositionVariableDeclarationNode(statementNode)
                    if (!hasSpace) {
                        if (endPosition.column == equalSignPosition.column - 1) {
                            return statements
                        } else {
                            val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 1)
                            val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                            val auxStatementList = statements.toMutableList()
                            val newVariableDeclarationNode =
                                createNewVariableDeclarationNode(
                                    statementNode.identifier,
                                    statementNode.expression,
                                    statementNode.keywordNode,
                                    statementNode.colonNode,
                                    statementNode.typeNode,
                                    newEqualSignNode,
                                    statementNode.getStart(),
                                    statementNode.getEnd(),
                                )
                            auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                            return auxStatementList
                        }
                    } else {
                        if (endPosition.column == equalSignPosition.column - 2) {
                            return statements
                        } else {
                            val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 2)
                            val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                            val auxStatementList = statements.toMutableList()
                            val newVariableDeclarationNode =
                                createNewVariableDeclarationNode(
                                    statementNode.identifier,
                                    statementNode.expression,
                                    statementNode.keywordNode,
                                    statementNode.colonNode,
                                    statementNode.typeNode,
                                    newEqualSignNode,
                                    statementNode.getStart(),
                                    statementNode.getEnd(),
                                )
                            auxStatementList[currentStatementIndex] = newVariableDeclarationNode
                            return auxStatementList
                        }
                    }
                }
            }
        }
    }

    private fun createNewEqualsNode(newEqualSignPosition: Position): EqualsNode {
        val newEqualSignNode = EqualsNode(newEqualSignPosition, newEqualSignPosition)
        return newEqualSignNode
    }

    // now the identifier node does not have a variable type node, so the end position is the end of the identifier
    private fun getEndPositionAssignationNode(statementNode: AssignationNode): Position {
        return statementNode.identifier.getEnd()
    }

    // now the variable type node is inside the variable declaration node
    private fun getEndPositionVariableDeclarationNode(statementNode: VariableDeclarationNode): Position {
        return statementNode.typeNode.getEnd()
    }
}
