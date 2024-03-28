package sca

import ast.AST
import sca.rule.FailurePayload
import sca.rule.Rule
import sca.rule.RuleFailures
import sca.rule.RuleSuccess

/**
 * Analyzes an AST using a list of rules and returns a report with the results
 *
 * @param rules List of rules to be used in the analysis
 * */
class StaticCodeAnalyzer(
    private val rules: List<Rule>,
) {
    /**
     * Analyzes an AST using the rules provided in the constructor
     *
     * @param ast AST to be analyzed
     * @return Report with the results of the analysis
     * */
    fun analyze(ast: AST): Report {
        val failures = mutableListOf<FailurePayload>()

        for (rule in rules) {
            when (val ruleResult = rule.check(ast)) {
                RuleSuccess -> {}
                is RuleFailures -> failures.addAll(ruleResult.failures)
            }
        }

        return Report(failures)
    }
}
