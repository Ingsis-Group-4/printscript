package sca.factory

import sca.provider.PrintLnArgumentNonExpressionRuleConfigurer
import sca.provider.StaticCodeAnalyzerConfigurer
import sca.provider.VariableNamingRuleConfigurer

/**
 * Factory for creating StaticCodeAnalyzerConfigurer.
 * It creates a StaticCodeAnalyzerConfigurer with a map with each specific RuleConfigurer mapped to the rule name
 * */
class StaticCodeAnalyzerConfigurerFactory {
    private val ruleConfigurers =
        mapOf(
            "variableNamingRule" to VariableNamingRuleConfigurer(),
            "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer(),
        )

    fun create(): StaticCodeAnalyzerConfigurer {
        return StaticCodeAnalyzerConfigurer(ruleConfigurers)
    }
}
