package rule

import formatter.rule.LineBreakAfterSemicolonRule
import formatter.rule.LineBreakBeforePrintLnRule
import formatter.rule.SpaceAfterColonRule
import formatter.rule.SpaceAfterEqualSignRule
import formatter.rule.SpaceBeforeColonRule
import formatter.rule.SpaceBeforeEqualSignRule
import formatter.rule.configurer.LineBreakAfterSemicolonConfigurer
import formatter.rule.configurer.LineBreakBeforePrintLnConfigurer
import formatter.rule.configurer.SpaceAfterColonConfigurer
import formatter.rule.configurer.SpaceAfterEqualSignConfigurer
import formatter.rule.configurer.SpaceBeforeColonConfigurer
import formatter.rule.configurer.SpaceBeforeEqualSignConfigurer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class RuleConfigurerTest {
    private val configPath = "src/test/resources/formatter.config.json"

    private fun readConfig(configPath: String): JsonObject {
        val fileContent = File(configPath).readText()
        val configJson = Json.parseToJsonElement(fileContent)
        return configJson.jsonObject
    }

    @Test
    fun `should return a LineBreakAfterSemicolon`() {
        val json = readConfig(configPath)
        val rule = LineBreakAfterSemicolonConfigurer().getRule(json)
        assertTrue(rule is LineBreakAfterSemicolonRule)
    }

    @Test
    fun `should return a SpaceAfterColonRule`() {
        val json = readConfig(configPath)
        val rule = SpaceAfterColonConfigurer().getRule(json)
        assertTrue(rule is SpaceAfterColonRule)
    }

    @Test
    fun `should return AfterEqualSignRule`() {
        val json = readConfig(configPath)
        val rule = SpaceAfterEqualSignConfigurer().getRule(json)
        assertTrue(rule is SpaceAfterEqualSignRule)
    }

    @Test
    fun `should return a SpaceBeforeColonRule`() {
        val json = readConfig(configPath)
        val rule = SpaceBeforeColonConfigurer().getRule(json)
        assertTrue(rule is SpaceBeforeColonRule)
    }

    @Test
    fun `should return a SpaceBeforeEqualSignConfigurer`() {
        val json = readConfig(configPath)
        val rule = SpaceBeforeEqualSignConfigurer().getRule(json)
        assertTrue(rule is SpaceBeforeEqualSignRule)
    }

    @Test
    fun `should return a LineBreakBeforePrintLnRule`() {
        val json = readConfig(configPath)
        val rule = LineBreakBeforePrintLnConfigurer().getRule(json)
        assertTrue(rule is LineBreakBeforePrintLnRule)
    }

    @Test
    fun `should throw an exception when rule config not found`() {
        val json = JsonObject(emptyMap())
        try {
            LineBreakAfterSemicolonConfigurer().getRule(json)
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message == "Rule config not found")
        }
    }
}
