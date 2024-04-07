package formatter.rule.factory

import formatter.rule.LineBreakAfterSemicolonRule
import formatter.rule.Rule
import formatter.rule.WhitespaceBeforeAndAfterOperatorRule
import formatter.rule.WhitespaceBetweenTokensRule
import formatter.rule.configurer.LineBreakAfterSemicolonConfigurer
import formatter.rule.configurer.LineBreakBeforePrintLnConfigurer
import formatter.rule.configurer.SpaceAfterColonConfigurer
import formatter.rule.configurer.SpaceAfterEqualSignConfigurer
import formatter.rule.configurer.SpaceBeforeColonConfigurer
import formatter.rule.configurer.SpaceBeforeEqualSignConfigurer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.File

class FormatterRuleFactory(configPath: String) {
    private val jsonFile: JsonObject

    init {
        jsonFile = readConfig(configPath)
    }

    private fun readConfig(configPath: String): JsonObject {
        val fileContent = File(configPath).readText()
        val configJson = Json.parseToJsonElement(fileContent)
        return configJson.jsonObject
    }

    private val rules: List<Rule> =
        listOf(
            SpaceAfterColonConfigurer().getRule(jsonFile),
            SpaceBeforeColonConfigurer().getRule(jsonFile),
            SpaceBeforeEqualSignConfigurer().getRule(jsonFile),
            SpaceAfterEqualSignConfigurer().getRule(jsonFile),
            WhitespaceBeforeAndAfterOperatorRule(true),
            LineBreakAfterSemicolonRule(true),
            WhitespaceBetweenTokensRule(true),
            LineBreakAfterSemicolonConfigurer().getRule(jsonFile),
            LineBreakBeforePrintLnConfigurer().getRule(jsonFile),
        )

    fun getRules(): List<Rule> {
        return rules
    }
}
