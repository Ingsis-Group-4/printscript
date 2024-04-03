package sca.factory

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import sca.configurer.PrintLnArgumentNonExpressionRuleConfigurer
import sca.configurer.VariableNamingRuleConfigurer
import sca.rule.Rule
import java.io.File

/**
 * Factory for creating the rule list of a StaticCodeAnalyzer.
 * */
interface SCARuleFactory {
    /**
     * Get the rules from a configuration file.
     *
     * @param configPath Path to the JSON configuration file
     * @return List of rules
     * */
    fun getRules(configPath: String): List<Rule>
}

class DefaultSCARuleFactory : SCARuleFactory {
    private val ruleConfigurers =
        mapOf(
            "variableNamingRule" to VariableNamingRuleConfigurer(),
            "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer(),
        )

    override fun getRules(configPath: String): List<Rule> {
        val config = readConfig(configPath)
        val rules =
            config.keys.map {
                    ruleName ->
                val ruleConfigurer = ruleConfigurers[ruleName] ?: throw IllegalArgumentException("Rule name $ruleName not found")
                ruleConfigurer.getRule(config)
            }

        return rules
    }

    private fun readConfig(configPath: String): JsonObject {
        // Read config from file
        val fileContent = File(configPath).readText()
        val configJson = Json.parseToJsonElement(fileContent)
        return configJson.jsonObject
    }
}
