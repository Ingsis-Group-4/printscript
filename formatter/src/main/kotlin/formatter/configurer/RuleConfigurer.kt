package formatter.configurer

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

interface RuleConfigurer<T> {
    fun getRule(): T
}

class SpaceBetweenColonConfigurer(configString: String) : RuleConfigurer<Boolean> {
    private val ruleConfigName = "hasSpaceBetweenColon"
    private val rule: Boolean = getJsonValue(readConfig(configString), ruleConfigName, String::toBoolean)

    override fun getRule(): Boolean = rule
}

class SpaceBetweenEqualSignConfigurer(configString: String) : RuleConfigurer<Boolean> {
    private val ruleConfigName = "hasSpaceBetweenEqualSign"
    private val rule: Boolean = getJsonValue(readConfig(configString), ruleConfigName, String::toBoolean)

    override fun getRule(): Boolean = rule
}

class LineBreakBeforePrintLnConfigurer(configString: String) : RuleConfigurer<Int> {
    private val ruleConfigName = "lineBreakBeforePrintLn"
    private val rule: Int = getJsonValue(readConfig(configString), ruleConfigName, String::toInt)

    override fun getRule(): Int = rule
}

class IfBlockIndentConfigurer(configString: String) : RuleConfigurer<Int> {
    private val ruleConfigName = "ifBlockIndent"
    private val rule: Int = getJsonValue(readConfig(configString), ruleConfigName, String::toInt)

    override fun getRule(): Int = rule
}

private fun readConfig(configString: String): JsonObject {
    val configJson = Json.parseToJsonElement(configString)
    return configJson.jsonObject
}

private fun <T> getJsonValue(
    json: JsonObject,
    key: String,
    convert: (String) -> T,
): T {
    if (json.containsKey(key)) {
        val value = json.getValue(key).jsonPrimitive.content
        return convert(value)
    } else {
        throw IllegalArgumentException("Rule config not found")
    }
}
