package org.example.factory

import org.example.provider.StaticCodeAnalyzerProvider
import org.example.provider.PrintLnArgumentNonExpressionRuleProvider
import org.example.provider.VariableNamingRuleProvider

class StaticCodeAnalyzerProviderFactory {

    private val ruleProviders = mapOf(
        "variableNamingRule" to VariableNamingRuleProvider(),
        "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleProvider()
    )

    fun create(): StaticCodeAnalyzerProvider {
        return StaticCodeAnalyzerProvider(ruleProviders)
    }
}