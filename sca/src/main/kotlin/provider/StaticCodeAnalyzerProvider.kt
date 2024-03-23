package org.example.provider

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.example.StaticCodeAnalyzer
import java.io.File

/**
 * Provider for StaticCodeAnalyzer. Receives a map of rule names to their respective RuleProvider
 * */
class StaticCodeAnalyzerProvider(
    private val providers: Map<String, RuleProvider>
){

    /**
     * Create a StaticCodeAnalyzer from a JSON configuration file.
     *
     * @throws IllegalArgumentException if a rule name is not found in the providers map
     * @param configPath Path to the JSON configuration file
     * */
    fun createStaticCodeAnalyzer(configPath: String): StaticCodeAnalyzer {
        val config = readConfig(configPath)
        val rules = config.keys.map {
            ruleName ->
                val provider = providers[ruleName] ?: throw IllegalArgumentException("Rule name $ruleName not found")
                provider.getRule(config)
        }
        return StaticCodeAnalyzer(rules)
    }

    private fun readConfig(configPath: String): JsonObject{
        // Read config from file
        val fileContent = File(configPath).readText()
        val configJson = Json.parseToJsonElement(fileContent)
        return configJson.jsonObject
    }
}