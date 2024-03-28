package org.example.factory

import org.example.provider.StaticCodeAnalyzerConfigurer
import org.example.provider.PrintLnArgumentNonExpressionRuleConfigurer
import org.example.provider.VariableNamingRuleConfigurer

/**
 * Factory for creating StaticCodeAnalyzerConfigurer.
 * It creates a StaticCodeAnalyzerConfigurer with a map with each specific RuleConfigurer mapped to the rule name
 * */
class StaticCodeAnalyzerConfigurerFactory {

    private val ruleConfigurers = mapOf(
        "variableNamingRule" to VariableNamingRuleConfigurer(),
        "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer()
    )

    fun create(): StaticCodeAnalyzerConfigurer {
        return StaticCodeAnalyzerConfigurer(ruleConfigurers)
    }
}