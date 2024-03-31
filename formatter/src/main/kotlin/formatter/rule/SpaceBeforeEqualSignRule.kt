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
        statementNode: StatementNode,
        index: Int,
        statements: List<StatementNode>,
    ): StatementNode {
        // maybe I'll have to divide it into two inner cases
        // it all depends on whether the identifier node has the variable type or not
        // because if it does the "token" that goes before the equal sign is the type
        // on the other hand if there is no variable type declared, the variable name is the token before the equal sign
        // that's in the case of the assignation node
        // in the case of the variable declaration node, I'll have to check first if there even is an assignation node in the first place, and then it's the same thinking as before
        when (statementNode) {
            is FunctionStatementNode -> {
                return statementNode
            }
            is AssignationNode -> {
                val equalSignPosition = statementNode.equalsNode.getStart()
                val endPosition = getEndPositionAssignationNode(statementNode)
                if (!hasSpace) {
                    if (endPosition.column == equalSignPosition.column - 1) {
                        return statementNode
                    } else {
                        val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 1)
                        val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                        return createNewAssignationNode(
                            statementNode.identifier,
                            statementNode.expression,
                            newEqualSignNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                } else {
                    if (endPosition.column == equalSignPosition.column - 2) {
                        return statementNode
                    } else {
                        val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 2)
                        val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                        return createNewAssignationNode(
                            statementNode.identifier,
                            statementNode.expression,
                            newEqualSignNode,
                            statementNode.getStart(),
                            statementNode.getEnd(),
                        )
                    }
                }
            }
            is VariableDeclarationNode -> {
                if (statementNode.equalsNode == null) {
                    return statementNode
                } else {
                    val equalSignPosition = statementNode.equalsNode!!.getStart()
                    val endPosition = getEndPositionVariableDeclarationNode(statementNode)
                    if (!hasSpace) {
                        if (endPosition.column == equalSignPosition.column - 1) {
                            return statementNode
                        } else {
                            val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 1)
                            val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                            return createNewVariableDeclarationNode(
                                statementNode.identifier,
                                statementNode.expression,
                                statementNode.keywordNode,
                                statementNode.colonNode,
                                newEqualSignNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
                        }
                    } else {
                        if (endPosition.column == equalSignPosition.column - 2) {
                            return statementNode
                        } else {
                            val newEqualSignPosition = Position(equalSignPosition.line, endPosition.column + 2)
                            val newEqualSignNode = createNewEqualsNode(newEqualSignPosition)
                            return createNewVariableDeclarationNode(
                                statementNode.identifier,
                                statementNode.expression,
                                statementNode.keywordNode,
                                statementNode.colonNode,
                                newEqualSignNode,
                                statementNode.getStart(),
                                statementNode.getEnd(),
                            )
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

    // function that checks if there is a variable type node or not, and returns each endPosition accordingly
    private fun getEndPositionAssignationNode(statementNode: AssignationNode): Position {
        return if (statementNode.identifier.variableType != null) {
            statementNode.identifier.variableType!!.getEnd()
        } else {
            statementNode.identifier.getEnd()
        }
    }

    private fun getEndPositionVariableDeclarationNode(statementNode: VariableDeclarationNode): Position {
        return if (statementNode.identifier.variableType != null) {
            statementNode.identifier.variableType!!.getEnd()
        } else {
            statementNode.identifier.getEnd()
        }
    }
}
// jdbjvb : String =
// 123456789012345678
