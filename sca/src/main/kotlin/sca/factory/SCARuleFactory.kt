package sca.factory

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import sca.configurer.PrintLnArgumentNonExpressionRuleConfigurer
import sca.configurer.VariableNamingRuleConfigurer
import sca.rule.Rule

/**
 * Factory for creating the rule list of a StaticCodeAnalyzer.
 * */
interface SCARuleFactory {
    /**
     * Get the rules from a configuration file.
     *
     * @param configString String in JSON format with rules config
     * @return List of rules
     * */
    fun getRules(configString: String): List<Rule>
}

class DefaultSCARuleFactory : SCARuleFactory {
    private val ruleConfigurers =
        mapOf(
            "variableNamingRule" to VariableNamingRuleConfigurer(),
            "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer(),
        )

    override fun getRules(configString: String): List<Rule> {
        val config = readConfig(configString)
        val rules =
            config.keys.map {
                    ruleName ->
                val ruleConfigurer = ruleConfigurers[ruleName] ?: throw IllegalArgumentException("Rule name $ruleName not found")
                ruleConfigurer.getRule(config)
            }

        return rules
    }

    private fun readConfig(configString: String): JsonObject {
        val configJson = Json.parseToJsonElement(configString)
        return configJson.jsonObject
    }
}
