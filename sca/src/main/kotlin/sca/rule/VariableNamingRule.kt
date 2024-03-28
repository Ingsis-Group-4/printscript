package sca.rule

import ast.AST
import ast.AssignationNode
import ast.FunctionStatementNode
import ast.ProgramNode
import ast.StatementNode
import ast.VariableDeclarationNode

/**
 * Rule that checks if variables in the AST follow a specific naming rule
 *
 * @param regex Regular expression that the variable names must match
 * */
class VariableNamingRule(
    private val regex: String,
) : Rule {
    override fun check(ast: AST): RuleResult {
        return when (ast) {
            is ProgramNode -> checkProgramNode(ast)
            is StatementNode -> checkStatementNode(ast)
            else -> RuleSuccess
        }
    }

    /**
     * Check if all statements in the ProgramNode follow the naming rule
     *
     * - If none of the statements fail the rule, RuleSuccess is returned
     * - If any statement fails the rule, RuleFailures with each failure is returned
     * */
    private fun checkProgramNode(ast: ProgramNode): RuleResult {
        val failures = mutableListOf<FailurePayload>()

        for (statement in ast.statements) {
            when (val statementCheckResult = checkStatementNode(statement)) {
                is RuleSuccess -> {}
                is RuleFailures -> {
                    failures.addAll(statementCheckResult.failures)
                }
            }
        }

        return if (failures.isEmpty()) {
            RuleSuccess
        } else {
            RuleFailures(failures)
        }
    }

    /**
     * Check if the statement follows the naming rule
     *
     * - If the statement is an AssignationNode or FunctionStatementNode, RuleSuccess is returned
     * - If the statement is a VariableDeclarationNode, check if variable naming is valid
     * */
    private fun checkStatementNode(ast: StatementNode): RuleResult {
        return when (ast) {
            is AssignationNode -> RuleSuccess
            is FunctionStatementNode -> RuleSuccess
            is VariableDeclarationNode -> checkVariableDeclaration(ast)
        }
    }

    /**
     * Check if the variable declaration follows the naming regex
     * */
    private fun checkVariableDeclaration(ast: VariableDeclarationNode): RuleResult {
        val variableName = ast.identifier.variableName

        return if (variableName.matches(regex.toRegex())) {
            RuleSuccess
        } else {
            RuleFailures(
                listOf(
                    FailurePayload(
                        "Variable '$variableName' does not follow naming rule",
                        ast.identifier.getStart(),
                    ),
                ),
            )
        }
    }
}
