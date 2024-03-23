package org.example.factory

import org.example.provider.StaticCodeAnalyzerConfigurer
import org.example.provider.PrintLnArgumentNonExpressionRuleConfigurer
import org.example.provider.VariableNamingRuleConfigurer

class StaticCodeAnalyzerProviderFactory {

    private val ruleConfigurers = mapOf(
        "variableNamingRule" to VariableNamingRuleConfigurer(),
        "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer()
    )

    fun create(): StaticCodeAnalyzerConfigurer {
        return StaticCodeAnalyzerConfigurer(ruleConfigurers)
    }
}