package sca

import position.Position
import sca.rule.FailurePayload

class Report(
    val ruleFailures: List<FailurePayload>,
) {
    fun getReportMessages(): List<String> {
        if (ruleFailures.isEmpty()) {
            return listOf("Static code analysis successful!")
        }

        val messages = mutableListOf<String>()

        for (failure in ruleFailures) {
            messages.add(getFailureMessage(failure))
        }

        return messages
    }

    private fun positionToString(position: Position): String {
        return "(line ${position.line}, column ${position.column})"
    }

    private fun getFailureMessage(failure: FailurePayload): String {
        return "Rule failed at ${positionToString(failure.position)}: ${failure.message}"
    }
}
