package formatter.configurer

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

interface RuleConfigurer<T> {
    fun getRule(): T
}

class SpaceBetweenColonConfigurer(configPath: String) : RuleConfigurer<Boolean> {
    private val ruleConfigName = "spaceBetweenColon"
    private val rule: Boolean = getJsonValue(readConfig(configPath), ruleConfigName, String::toBoolean)
    override fun getRule(): Boolean = rule
}

class SpaceBetweenEqualSignConfigurer(configPath: String) : RuleConfigurer<Boolean> {
    private val ruleConfigName = "spaceBetweenEquals"
    private val rule: Boolean = getJsonValue(readConfig(configPath), ruleConfigName, String::toBoolean)
    override fun getRule(): Boolean = rule
}

class LineBreakBeforePrintLnConfigurer(configPath: String) : RuleConfigurer<Int> {
    private val ruleConfigName = "lineBreakBeforePrintLn"
    private val rule: Int = getJsonValue(readConfig(configPath), ruleConfigName, String::toInt)
    override fun getRule(): Int = rule
}

private fun readConfig(configPath: String): JsonObject {
    val fileContent = File(configPath).readText()
    val configJson = Json.parseToJsonElement(fileContent)
    return configJson.jsonObject
}

private fun <T> getJsonValue(json: JsonObject, key: String, convert: (String) -> T): T {
    if (json.containsKey(key)) {
        val value = json.getValue(key).jsonPrimitive.content
        return convert(value)
    } else {
        throw IllegalArgumentException("Rule config not found")
    }
}
