package sca

import ast.ProgramNode
import ast.StatementNode
import sca.rule.FailurePayload
import sca.rule.RuleFailures
import sca.rule.RuleResult
import sca.rule.RuleSuccess

/**
 * Check if all statements in the ProgramNode follow the println rule
 *
 * - If none of the statements fail the rule, RuleSuccess is returned
 * - If any statement fails the rule, RuleFailures with each failure is returned
 * */
fun checkProgramNode(
    ast: ProgramNode,
    checkStatementNode: (StatementNode) -> RuleResult,
): RuleResult {
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
