package sca

import position.Position
import sca.rule.FailurePayload

class Report(
    val ruleFailures: List<FailurePayload>,
) {
    fun print() {
        if (ruleFailures.isEmpty()) {
            println("Static code analysis successful!")
            return
        }
        for (failure in ruleFailures) {
            printFailure(failure)
        }
    }

    private fun positionToString(position: Position): String {
        return "(${position.line}, column ${position.column})"
    }

    private fun printFailure(failure: FailurePayload) {
        println("Rule failed at ${positionToString(failure.position)}: ${failure.message}")
    }
}
